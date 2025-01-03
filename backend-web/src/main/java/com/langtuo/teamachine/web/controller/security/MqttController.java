package com.langtuo.teamachine.web.controller.security;

import com.langtuo.teamachine.api.model.security.MqttTokenDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.security.MqttService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityset/mqtt")
public class MqttController {
    @Resource
    private MqttService mqttService;

    /**
     * url: http://localhost:8080/teamachinebackend/securityset/mqtt/token/get?tenantCode=tenant_001&machineCode=111
     * @return
     */
    @GetMapping(value = "/token/getbymachinecode")
    public IceMachineResult<MqttTokenDTO> getMqttTokenByMachineCode(@RequestParam("tenantCode") String tenantCode,
                                                                    @RequestParam("machineCode") String machineCode) {
        IceMachineResult<MqttTokenDTO> rtn = mqttService.getMqttToken(tenantCode, machineCode);
        return rtn;
    }
}
