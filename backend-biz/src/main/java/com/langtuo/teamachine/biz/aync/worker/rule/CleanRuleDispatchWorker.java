package com.langtuo.teamachine.biz.aync.worker.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.rule.CleanRuleDTO;
import com.langtuo.teamachine.biz.convertor.rule.CleanRuleMgtConvertor;
import com.langtuo.teamachine.biz.manager.MachineManager;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.biz.manager.ShopManager;
import com.langtuo.teamachine.biz.util.SpringManagerUtils;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.accessor.rule.CleanRuleAccessor;
import com.langtuo.teamachine.dao.accessor.rule.CleanRuleDispatchAccessor;
import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import com.langtuo.teamachine.dao.po.rule.CleanRulePO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CleanRuleDispatchWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 管理面登录名称
     */
    private String loginName;

    /**
     * 清洁规则编码
     */
    private String cleanRuleCode;

    public CleanRuleDispatchWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.loginName = jsonPayload.getString(CommonConsts.JSON_KEY_LOGIN_NAME);
        this.cleanRuleCode = jsonPayload.getString(CommonConsts.JSON_KEY_CLEAN_RULE_CODE);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(cleanRuleCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";cleanRuleCode=" + cleanRuleCode);
            throw new IllegalArgumentException("tenantCode or cleanRuleCode is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();
        if (jsonDispatchCont == null) {
            log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";cleanRuleCode=" + cleanRuleCode);
            return;
        }

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_CLEAN_RULE);
        jsonMsg.put(CommonConsts.JSON_KEY_CLEAN_RULE, jsonDispatchCont);

        // 准备发送
        List<String> machineCodeList = getMachineCodeList();
        if (CollectionUtils.isEmpty(machineCodeList)) {
            log.error("getMachineCodeList|empty|tenantCode=" + tenantCode + ";cleanRuleCode=" + cleanRuleCode);
            return;
        }

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        for (String machineCode : machineCodeList) {
            mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
        }
    }

    private JSONObject getDispatchCont() {
        CleanRuleAccessor cleanRuleAccessor = SpringAccessorUtils.getCleanRuleAccessor();
        CleanRulePO po = cleanRuleAccessor.getByCleanRuleCode(tenantCode, cleanRuleCode);
        CleanRuleDTO dto = CleanRuleMgtConvertor.convertToCleanRuleStepDTO(po);
        if (dto == null) {
            log.error("getRule|error|tenantCode=" + tenantCode + ";cleanRuleCode=" + cleanRuleCode);
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(dto);
        return jsonObject;
    }

    private List<String> getMachineCodeList() {
        ShopGroupManager shopGroupManager = SpringManagerUtils.getShopGroupManager();
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginName(tenantCode, loginName);

        CleanRuleDispatchAccessor cleanRuleDispatchAccessor = SpringAccessorUtils.getCleanRuleDispatchAccessor();
        List<ConfigRuleDispatchPO> configRuleDispatchPOList = cleanRuleDispatchAccessor.listByCleanRuleCode(
                tenantCode, cleanRuleCode, shopGroupCodeList);
        if (CollectionUtils.isEmpty(configRuleDispatchPOList)) {
            log.error("getDispatchPOList|error|tenantCode=" + tenantCode + ";cleanRuleCode=" + cleanRuleCode);
            return null;
        }

        ShopManager shopManager = SpringManagerUtils.getShopManager();
        List<String> shopCodeList = shopManager.getShopCodeListByShopGroupCodeList(tenantCode,
                configRuleDispatchPOList.stream()
                        .map(ConfigRuleDispatchPO::getShopGroupCode)
                        .collect(Collectors.toList()));

        MachineManager machineManager = SpringManagerUtils.getMachineManager();
        List<String> machineCodeList = machineManager.getMachineCodeListByShopCodeList(tenantCode, shopCodeList);
        return machineCodeList;
    }
}
