package com.langtuo.teamachine.web.controller.security;

import com.langtuo.teamachine.api.model.security.OssTokenDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.security.OssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityset/oss")
public class OssController {
    @Resource
    private OssService ossService;

    /**
     * url: http://localhost:8080/teamachinebackend/securityset/oss/token/get?tenantCode=tenant_001&machineCode=111
     * @return
     */
    @GetMapping(value = "/token/getbymachinecode")
    public TeaMachineResult<OssTokenDTO> getOssTokenByMachineCode(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("machineCode") String machineCode) {
        TeaMachineResult<OssTokenDTO> rtn = ossService.getOssTokenByMachineCode(tenantCode, machineCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/securityset/oss/token/get?tenantCode=tenant_001&loginName=111
     * @return
     */
    @GetMapping(value = "/token/getbyloginname")
    public TeaMachineResult<OssTokenDTO> getOssTokenByLoginName(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("loginName") String loginName) {
        TeaMachineResult<OssTokenDTO> rtn = ossService.getOssTokenByLoginName(tenantCode, loginName);
        return rtn;
    }
}
