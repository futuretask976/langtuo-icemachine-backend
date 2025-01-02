package com.langtuo.teamachine.biz.aync.worker.device;

import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.device.AndroidAppDTO;
import com.langtuo.teamachine.biz.convertor.device.AndroidAppMgtConvertor;
import com.langtuo.teamachine.biz.manager.MachineManager;
import com.langtuo.teamachine.biz.util.SpringManagerUtils;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.accessor.device.AndroidAppAccessor;
import com.langtuo.teamachine.dao.accessor.device.AndroidAppDispatchAccessor;
import com.langtuo.teamachine.dao.po.device.AndroidAppDispatchPO;
import com.langtuo.teamachine.dao.po.device.AndroidAppPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AndroidAppDispatchWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 型号编码
     */
    private String version;

    public AndroidAppDispatchWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.version = jsonPayload.getString(CommonConsts.JSON_KEY_VERSION);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(version)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";version=" + version);
            throw new IllegalArgumentException("tenantCode or version is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();
        if (jsonDispatchCont == null) {
            log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";version=" + version);
            return;
        }

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_ANDROID_APP);
        jsonMsg.put(CommonConsts.JSON_KEY_MODEL, jsonDispatchCont);

        // 准备发送
        List<String> machineCodeList = getMachineCodeList();
        if (CollectionUtils.isEmpty(machineCodeList)) {
            log.error("getMachineCodeList|empty|tenantCode=" + tenantCode + ";version=" + version);
        }

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        for (String machineCode : machineCodeList) {
            mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
        }
    }

    private List<String> getMachineCodeList() {
        AndroidAppDispatchAccessor androidAppDispatchAccessor = SpringAccessorUtils.getAndroidAppDispatchAccessor();
        List<AndroidAppDispatchPO> androidAppDispatchPOList = androidAppDispatchAccessor.listByVersion(
                tenantCode, version);
        if (CollectionUtils.isEmpty(androidAppDispatchPOList)) {
            log.error("listByVersion|error|tenantCode=" + tenantCode + ";version=" + version);
            return null;
        }

        ShopManager shopManager = SpringManagerUtils.getShopManager();
        List<String> shopCodeList = shopManager.getShopCodeListByShopGroupCodeList(tenantCode,
                androidAppDispatchPOList.stream()
                        .map(AndroidAppDispatchPO::getShopGroupCode)
                        .collect(Collectors.toList()));

        MachineManager machineManager = SpringManagerUtils.getMachineManager();
        List<String> machineCodeList = machineManager.getMachineCodeListByShopCodeList(tenantCode, shopCodeList);
        return machineCodeList;
    }

    private JSONObject getDispatchCont() {
        AndroidAppAccessor androidAppAccessor = SpringAccessorUtils.getAndroidAppAccessor();
        AndroidAppPO po = androidAppAccessor.getByVersion(version);
        AndroidAppDTO dto = AndroidAppMgtConvertor.convertToAndroidAppDTO(po);
        if (dto == null) {
            log.error("getAndroidApp|error|tenantCode=" + tenantCode + ";version=" + version);
            return null;
        }

        JSONObject jsonDispatchCont = (JSONObject) JSONObject.toJSON(dto);
        return jsonDispatchCont;
    }
}
