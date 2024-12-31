package com.langtuo.teamachine.biz.service.security;

import com.langtuo.teamachine.api.model.security.MqttTokenDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.security.MqttService;
import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import com.langtuo.teamachine.mqtt.model.MqttToken;
import com.langtuo.teamachine.mqtt.util.MqttUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MqttServiceImpl implements MqttService {
    @Resource
    private MachineAccessor machineAccessor;

    @Override
    public IceMachineResult<MqttTokenDTO> getMqttToken(String tenantCode, String machineCode) {
        MachinePO po = machineAccessor.getByMachineCode(tenantCode, machineCode);
        if (po == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }
        if (CommonConsts.STATE_DISABLED == po.getState()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_DISABLED_MACHINE_APPLY_TOKEN));
        }

        MqttToken mqttToken = MqttUtils.getMqttToken(tenantCode);
        MqttTokenDTO dto = new MqttTokenDTO();
        dto.setAccessToken(mqttToken.getAccessToken());
        dto.setAccessKey(mqttToken.getAccessKey());
        dto.setExpiration(mqttToken.getExpiration());
        return IceMachineResult.success(dto);
    }
}
