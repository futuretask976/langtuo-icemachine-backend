package com.langtuo.teamachine.biz.aync.worker.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.rule.DrainRuleNewDTO;
import com.langtuo.teamachine.biz.convertor.rule.DrainRuleMgtConvertor;
import com.langtuo.teamachine.biz.manager.MachineManager;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.biz.manager.ShopManager;
import com.langtuo.teamachine.biz.util.SpringManagerUtils;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DrainRuleDispatchNewWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 管理面登录名称
     */
    private String loginName;

    /**
     * 开业规则编码
     */
    private String drainRuleCode;

    public DrainRuleDispatchNewWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.loginName = jsonPayload.getString(CommonConsts.JSON_KEY_LOGIN_NAME);
        this.drainRuleCode = jsonPayload.getString(CommonConsts.JSON_KEY_DRAIN_RULE_CODE);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(drainRuleCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";drainRuleCode=" + drainRuleCode);
            throw new IllegalArgumentException("tenantCode or drainRuleCode is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();
        if (jsonDispatchCont == null) {
            log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";drainRuleCode=" + drainRuleCode);
            return;
        }

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_DRAIN_RULE_NEW);
        jsonMsg.put(CommonConsts.JSON_KEY_DRAIN_RULE, jsonDispatchCont);

        // 准备发送
        List<String> machineCodeList = getMachineCodeList();
        if (CollectionUtils.isEmpty(machineCodeList)) {
            log.error("getMachineCodeList|empty|tenantCode=" + tenantCode + ";drainRuleCode=" + drainRuleCode);
            return;
        }

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        for (String machineCode : machineCodeList) {
            mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
        }
    }

    private JSONObject getDispatchCont() {
        DrainRuleAccessor drainRuleAccessor = SpringAccessorUtils.getDrainRuleNewAccessor();
        DrainRulePO po = drainRuleAccessor.getByDrainRuleCode(tenantCode, drainRuleCode);
        DrainRuleNewDTO dto = DrainRuleMgtConvertor.convertToDrainRuleDTO(po);
        if (dto == null) {
            log.error("getRule|error|tenantCode=" + tenantCode + ";drainRuleCode=" + drainRuleCode);
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(dto);
        return jsonObject;
    }

    private List<String> getMachineCodeList() {
        ShopGroupManager shopGroupManager = SpringManagerUtils.getShopGroupManager();
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginName(tenantCode, loginName);

        DrainRuleDispatchAccessor drainRuleDispatchAccessor = SpringAccessorUtils.getDrainRuleDispatchNewAccessor();
        List<DrainRuleDispatchPO> drainRuleDispatchPOList = drainRuleDispatchAccessor.listByDrainRuleCode(
                tenantCode, drainRuleCode, shopGroupCodeList);
        if (CollectionUtils.isEmpty(drainRuleDispatchPOList)) {
            log.error("getDispatchPOList|error|tenantCode=" + tenantCode + ";drainRuleCode=" + drainRuleCode);
            return null;
        }

        ShopManager shopManager = SpringManagerUtils.getShopManager();
        List<String> shopCodeList = shopManager.getShopCodeListByShopGroupCodeList(tenantCode,
                drainRuleDispatchPOList.stream()
                        .map(DrainRuleDispatchPO::getShopGroupCode)
                        .collect(Collectors.toList()));

        MachineManager machineManager = SpringManagerUtils.getMachineManager();
        List<String> machineCodeList = machineManager.getMachineCodeListByShopCodeList(tenantCode, shopCodeList);
        return machineCodeList;
    }
}
