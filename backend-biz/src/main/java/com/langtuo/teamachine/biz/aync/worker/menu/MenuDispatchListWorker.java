package com.langtuo.teamachine.biz.aync.worker.menu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.biz.manager.MenuManager;
import com.langtuo.teamachine.biz.util.BizUtils;
import com.langtuo.teamachine.biz.util.SpringManagerUtils;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchCacheAccessor;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchCachePO;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;

@Slf4j
public class MenuDispatchListWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 店铺编码组
     */
    private String shopGroupCode;

    public MenuDispatchListWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.shopGroupCode = jsonPayload.getString(CommonConsts.JSON_KEY_SHOP_GROUP_CODE);
        this.machineCode = jsonPayload.getString(CommonConsts.JSON_KEY_MACHINE_CODE);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(shopGroupCode) || StringUtils.isBlank(machineCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
            throw new IllegalArgumentException("tenantCode or shopGroupCode or machineCode is blank");
        }
    }

    @Override
    public void run() {
        String fileName = BizUtils.getMenuListFileName(shopGroupCode);
        File tmpFile = new File(CommonConsts.MENU_OUTPUT_PATH + fileName);
        try {
            MenuDispatchCacheAccessor menuDispatchCacheAccessor = SpringAccessorUtils.getMenuDispatchHistoryAccessor();
            MenuDispatchCachePO existOssPO = menuDispatchCacheAccessor.getByFileName(tenantCode,
                    CommonConsts.MENU_DISPATCH_LIST_TRUE, fileName);
            if (existOssPO != null) {
                sendToMachine(getSendMsg(existOssPO));
                return;
            }

            if (tmpFile.exists()) {
                log.error("tmpFileCheck|exist|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode + ";tmpFile=" + tmpFile.getAbsolutePath());
                return;
            }

            JSONArray dispatchCont = getDispatchCont();
            if (dispatchCont == null) {
                log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
                return;
            }

            boolean wrote = BizUtils.writeStrToFile(dispatchCont.toJSONString(), tmpFile);
            if (!wrote) {
                log.error("writeStrToFile|error|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
                return;
            }
            String ossPath = BizUtils.uploadOSS(tmpFile);
            if (StringUtils.isBlank(ossPath)) {
                log.error("uploadOSS|error|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
                return;
            }
            String md5AsHex = BizUtils.calcMD5Hex(tmpFile);
            if (StringUtils.isBlank(md5AsHex)) {
                log.error("calcMD5Hex|error|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
                return;
            }

            MenuDispatchCachePO newOssPO = getNewCachePO(fileName, md5AsHex);
            int inserted = menuDispatchCacheAccessor.insert(newOssPO);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                log.error("insertOssInfo|error|inserted=" + inserted + ";tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
            }

            sendToMachine(getSendMsg(newOssPO));
        } catch (Exception e) {
            log.error("run|fatal|e=" + e.getMessage(), e);
        } finally {
            tmpFile.delete();
        }
    }

    private JSONArray getDispatchCont() {
        MenuManager menuManager = SpringManagerUtils.getMenuManager();
        List<MenuPO> menuPOList = menuManager.getMenuPOListByShopGroupCode(tenantCode, shopGroupCode);
        if (CollectionUtils.isEmpty(menuPOList)) {
            log.error("getMenu|empty|tenantCode=" + tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
            return null;
        }
        menuPOList.sort((o1, o2) -> o1.getGmtModified().equals(o2.getGmtModified()) ?
                0 : o1.getGmtModified().before(o2.getGmtModified()) ? 1 : -1);

        JSONArray arr = new JSONArray();
        for (MenuPO menuPO : menuPOList) {
            JSONObject menuDispatchCont = BizUtils.getMenuDispatchCont(tenantCode, menuPO.getMenuCode());
            arr.add(menuDispatchCont);
        }
        return arr;
    }

    private JSONObject getSendMsg(MenuDispatchCachePO po) {
        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_MENU_LIST);
        jsonMsg.put(CommonConsts.JSON_KEY_MD5_AS_HEX, po.getMd5());
        jsonMsg.put(CommonConsts.JSON_KEY_OSS_PATH,
                AliyunConsts.OSS_MENU_PATH + AliyunConsts.OSS_PATH_SEPARATOR + po.getFileName());
        return jsonMsg;
    }

    private void sendToMachine(JSONObject jsonMsg) {
        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
    }

    private MenuDispatchCachePO getNewCachePO(String fileName, String md5AsHex) {
        MenuDispatchCachePO newCachePO = new MenuDispatchCachePO();
        newCachePO.setTenantCode(tenantCode);
        newCachePO.setInit(CommonConsts.MENU_DISPATCH_LIST_TRUE);
        newCachePO.setFileName(fileName);
        newCachePO.setMd5(md5AsHex);
        return newCachePO;
    }
}
