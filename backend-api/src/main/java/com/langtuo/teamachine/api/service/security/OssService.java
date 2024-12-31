package com.langtuo.teamachine.api.service.security;

import com.langtuo.teamachine.api.model.security.OssTokenDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;

public interface OssService {
    /**
     *
     * @return
     */
    IceMachineResult<OssTokenDTO> getOssTokenByMachineCode(String tenantCode, String machineCode);

    /**
     *
     * @return
     */
    IceMachineResult<OssTokenDTO> getOssTokenByLoginName(String tenantCode, String loginName);
}
