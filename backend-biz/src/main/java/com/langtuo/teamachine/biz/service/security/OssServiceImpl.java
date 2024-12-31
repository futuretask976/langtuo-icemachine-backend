package com.langtuo.teamachine.biz.service.security;

import com.langtuo.teamachine.api.model.security.OssTokenDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.security.OssService;
import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.oss.OssUtils;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.po.security.OssToken;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@Component
@Slf4j
public class OssServiceImpl implements OssService {
    @Resource
    private MachineAccessor machineAccessor;

    @Override
    public TeaMachineResult<OssTokenDTO> getOssTokenByMachineCode(String tenantCode, String machineCode) {
        MachinePO machinePO = machineAccessor.getByMachineCode(tenantCode, machineCode);
        if (machinePO == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }
        if (CommonConsts.STATE_DISABLED == machinePO.getState()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_DISABLED_MACHINE_APPLY_TOKEN));
        }

        OssToken po = OssUtils.getSTS();
        OssTokenDTO dto = new OssTokenDTO();
        dto.setRegion(po.getRegion());
        dto.setBucketName(po.getBucketName());
        dto.setAccessKeyId(po.getAccessKeyId());
        dto.setAccessKeySecret(po.getAccessKeySecret());
        dto.setSecurityToken(po.getSecurityToken());
        dto.setRequestId(po.getRequestId());
        dto.setExpiration(po.getExpiration());
        return TeaMachineResult.success(dto);
    }

    @Override
    public TeaMachineResult<OssTokenDTO> getOssTokenByLoginName(String tenantCode, String loginName) {
        log.debug("getOssTokenByLoginName|entering|tenantCode=" + tenantCode + ";loginName=" + loginName);

        OssToken po = OssUtils.getSTS();
        OssTokenDTO dto = new OssTokenDTO();
        dto.setRegion(po.getRegion());
        dto.setBucketName(po.getBucketName());
        dto.setAccessKeyId(po.getAccessKeyId());
        dto.setAccessKeySecret(po.getAccessKeySecret());
        dto.setSecurityToken(po.getSecurityToken());
        dto.setRequestId(po.getRequestId());
        dto.setExpiration(po.getExpiration());
        return TeaMachineResult.success(dto);
    }
}
