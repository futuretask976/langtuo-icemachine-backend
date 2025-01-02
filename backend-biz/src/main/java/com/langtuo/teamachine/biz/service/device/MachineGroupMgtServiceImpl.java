package com.langtuo.teamachine.biz.service.device;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.MachineGroupDTO;
import com.langtuo.teamachine.api.request.device.MachineGroupPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.device.MachineGroupMgtService;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.device.MachineGroupMgtConvertor.convert;

@Component
@Slf4j
public class MachineGroupMgtServiceImpl implements MachineGroupMgtService {
    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<MachineGroupDTO> getByShopGroupCode(String tenantCode, String shopGroupCode) {
        MachineGroupPO machineGroupPO = machineGroupAccessor.getByMachineGroupCode(tenantCode, shopGroupCode);
        MachineGroupDTO machineGroupDTO = convert(machineGroupPO);
        return IceMachineResult.success(machineGroupDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<MachineGroupDTO>> search(String tenantCode, String machineGroupName,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<MachineGroupPO> pageInfo = machineGroupAccessor.search(tenantCode, machineGroupName,
                    pageNum, pageSize);

            return IceMachineResult.success(new PageDTO<>(convert(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<MachineGroupDTO>> list(String tenantCode) {
        try {
            List<MachineGroupPO> list = machineGroupAccessor.list(tenantCode);
            return IceMachineResult.success(convert(list));
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> put(MachineGroupPutRequest request) {
        if (request == null || !request.isValid()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        MachineGroupPO machineGroupPO = convert(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(machineGroupPO);
            } else {
                return doPutUpdate(machineGroupPO);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutNew(MachineGroupPO po) {
        try {
            MachineGroupPO exist = machineGroupAccessor.getByMachineGroupCode(po.getTenantCode(), po.getMachineGroupCode());
            if (exist != null) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
            }

            int inserted = machineGroupAccessor.insert(po);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                log.error("putNew|error|" + inserted);
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("putUpdate|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutUpdate(MachineGroupPO po) {
        try {
            MachineGroupPO exist = machineGroupAccessor.getByMachineGroupCode(po.getTenantCode(), po.getMachineGroupCode());
            if (exist == null) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
            }

            int updated = machineGroupAccessor.update(po);
            if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
                log.error("putUpdate|error|" + updated);
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("putUpdate|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> deleteByMachineGroupCode(String tenantCode, String machineGroupCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(machineGroupCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            machineGroupAccessor.deleteByShopGroupCode(tenantCode, machineGroupCode);
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}
