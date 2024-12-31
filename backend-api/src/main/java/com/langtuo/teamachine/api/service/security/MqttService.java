package com.langtuo.teamachine.api.service.security;

import com.langtuo.teamachine.api.model.security.MqttTokenDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;

public interface MqttService {
    /**
     *
     * @return
     */
    TeaMachineResult<MqttTokenDTO> getMqttToken(String tenantCode, String machineCode);
}
