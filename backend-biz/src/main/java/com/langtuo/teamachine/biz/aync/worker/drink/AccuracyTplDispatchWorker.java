package com.langtuo.teamachine.biz.aync.worker.drink;

import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.drink.AccuracyTplDTO;
import com.langtuo.teamachine.biz.convertor.drink.AccuracyTplMgtConvertor;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.accessor.drink.AccuracyTplAccessor;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class AccuracyTplDispatchWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 模板编码
     */
    private String templateCode;

    public AccuracyTplDispatchWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.templateCode = jsonPayload.getString(CommonConsts.JSON_KEY_TEMPLATE_CODE);
        if (StringUtils.isBlank(tenantCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";templateCode=" + templateCode);
            throw new IllegalArgumentException("tenantCode or templateCode is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();
        if (jsonDispatchCont == null) {
            log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";templateCode=" + templateCode);
            return;
        }

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_ACCURACY);
        jsonMsg.put(CommonConsts.JSON_KEY_ACCURACY_TPL, jsonDispatchCont);

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        mqttProducer.sendBroadcastMsgByTenant(tenantCode, jsonMsg.toJSONString());
    }

    private JSONObject getDispatchCont() {
        AccuracyTplAccessor accuracyTplAccessor = SpringAccessorUtils.getAccuracyTplAccessor();
        AccuracyTplPO po = accuracyTplAccessor.getByTplCode(tenantCode, templateCode);
        AccuracyTplDTO dto = AccuracyTplMgtConvertor.convertToAccuracyTplPO(po);
        if (dto == null) {
            log.error("getTpl|error|tenantCode=" + tenantCode + ";templateCode=" + templateCode);
            return null;
        }

        JSONObject jsonDispatchCont = (JSONObject) JSONObject.toJSON(dto);
        return jsonDispatchCont;
    }
}