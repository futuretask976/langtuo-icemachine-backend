package com.langtuo.teamachine.biz.aync.worker.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class WarningRuleDispatchWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 管理面登录名称
     */
    private String loginName;

    /**
     * 预警规则编码
     */
    private String warningRuleCode;

    public WarningRuleDispatchWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.loginName = jsonPayload.getString(CommonConsts.JSON_KEY_LOGIN_NAME);
        this.warningRuleCode = jsonPayload.getString(CommonConsts.JSON_KEY_WARNING_RULE_CODE);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(warningRuleCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";warningRuleCode=" + warningRuleCode);
            throw new IllegalArgumentException("tenantCode or warningRuleCode is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_WARNING_RULE);
        jsonMsg.put(CommonConsts.JSON_KEY_WARNING_RULE, jsonDispatchCont);

        // 准备发送
        List<String> machineCodeList = getMachineCodeList();
        if (CollectionUtils.isEmpty(machineCodeList)) {
            log.error("getMachineCodeList|empty|tenantCode=" + tenantCode + ";warningRuleCode=" + warningRuleCode);
            return;
        }

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        for (String machineCode : machineCodeList) {
            mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
        }
    }

    private JSONObject getDispatchCont() {
        WarningRuleAccessor warningRuleAccessor = SpringAccessorUtils.getWarningRuleAccessor();
        WarningRulePO po = warningRuleAccessor.getByWarningRuleCode(tenantCode, warningRuleCode);
        WarningRuleDTO dto = WarningRuleMgtConvertor.convertToWarningRuleDTO(po);
        if (dto == null) {
            log.error("getRule|error|tenantCode=" + tenantCode + ";warningRuleCode=" + warningRuleCode);
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(dto);
        return jsonObject;
    }

    private List<String> getMachineCodeList() {
        ShopGroupManager shopGroupManager = SpringManagerUtils.getShopGroupManager();
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginName(tenantCode, loginName);

        WarningRuleDispatchAccessor warningRuleDispatchAccessor = SpringAccessorUtils.getWarningRuleDispatchAccessor();
        List<WarningRuleDispatchPO> warningRuleDispatchPOList = warningRuleDispatchAccessor.listByWarningRuleCode(
                tenantCode, warningRuleCode, shopGroupCodeList);
        if (CollectionUtils.isEmpty(warningRuleDispatchPOList)) {
            log.error("getDispatchPOList|error|tenantCode=" + tenantCode + ";warningRuleCode=" + warningRuleCode);
            return null;
        }

        ShopManager shopManager = SpringManagerUtils.getShopManager();
        List<String> shopCodeList = shopManager.getShopCodeListByShopGroupCodeList(tenantCode,
                warningRuleDispatchPOList.stream()
                        .map(WarningRuleDispatchPO::getShopGroupCode)
                        .collect(Collectors.toList()));

        MachineManager machineManager = SpringManagerUtils.getMachineManager();
        List<String> machineCodeList = machineManager.getMachineCodeListByShopCodeList(tenantCode, shopCodeList);
        return machineCodeList;
    }
}
