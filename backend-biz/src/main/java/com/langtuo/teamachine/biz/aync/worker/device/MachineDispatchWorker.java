package com.langtuo.teamachine.biz.aync.worker.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.biz.convertor.device.MachineMgtConvertor;
import com.langtuo.teamachine.biz.util.SpringServiceUtils;
import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class MachineDispatchWorker implements Runnable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 机器编码
     */
    private String machineCode;

    public MachineDispatchWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        this.machineCode = jsonPayload.getString(CommonConsts.JSON_KEY_MACHINE_CODE);
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(machineCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode + ";machineCode=" + machineCode);
            throw new IllegalArgumentException("tenantCode or machineCode is blank");
        }
    }

    @Override
    public void run() {
        JSONObject jsonDispatchCont = getDispatchCont();
        if (jsonDispatchCont == null) {
            log.error("getDispatchCont|error|tenantCode=" + tenantCode + ";machineCode=" + machineCode);
            return;
        }

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DISPATCH_MACHINE);
        jsonMsg.put(CommonConsts.JSON_KEY_MACHINE, jsonDispatchCont);

        MqttProducer mqttProducer = SpringServiceUtils.getMqttProducer();
        mqttProducer.sendP2PMsgByTenant(tenantCode, machineCode, jsonMsg.toJSONString());
    }

    private JSONObject getDispatchCont() {
        MachineAccessor machineAccessor = SpringAccessorUtils.getMachineAccessor();
        MachinePO po = machineAccessor.getByMachineCode(tenantCode, machineCode);
        MachineDTO dto = MachineMgtConvertor.convert(po);
        if (dto == null) {
            log.error("getMachine|error|tenantCode=" + tenantCode + ";machineCode=" + machineCode);
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(dto);
        return jsonObject;
    }
}
