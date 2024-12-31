package com.langtuo.teamachine.api.service.security;

import com.langtuo.teamachine.api.model.security.MqttTokenDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;

public interface MqttService {
    /**
     *
     * @return
     */
    IceMachineResult<MqttTokenDTO> getMqttToken(String tenantCode, String machineCode);
}
