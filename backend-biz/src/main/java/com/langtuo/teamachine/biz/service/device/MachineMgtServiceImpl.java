package com.langtuo.teamachine.biz.service.device;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.device.MachineMgtService;
import com.langtuo.teamachine.biz.aync.AsyncDispatcher;
import com.langtuo.teamachine.biz.manager.ShopManager;
import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.device.MachineMgtConvertor.convert;
import static com.langtuo.teamachine.biz.convertor.device.MachineMgtConvertor.convertToMachinePO;

@Component
@Slf4j
public class MachineMgtServiceImpl implements MachineMgtService {
    @Resource
    private ShopManager shopManager;

    @Resource
    private MachineAccessor machineAccessor;

    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    @Resource
    private AsyncDispatcher asyncDispatcher;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<MachineDTO> getByMachineCode(String tenantCode, String machineCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(machineCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            MachinePO po = machineAccessor.getByMachineCode(tenantCode, machineCode);
            MachineDTO dto = convert(po);

            return IceMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByMachineCode|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<MachineDTO>> search(String tenantCode, String machineCode, String screenCode,
                                                        String elecBoardCode, String shopCode, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            List<String> shopCodeList = Lists.newArrayList();
            if (StringUtils.isBlank(shopCode)) {
                shopCodeList.addAll(shopManager.getShopCodeListByLoginSession(tenantCode));
            } else {
                shopCodeList.add(shopCode);
            }

            PageInfo<MachinePO> pageInfo = machineAccessor.search(tenantCode, machineCode, screenCode, elecBoardCode,
                    shopCodeList, pageNum, pageSize);
            List<MachineDTO> dtoList = convert(pageInfo.getList());

            return IceMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<MachineDTO>> list(String tenantCode) {
        if (StringUtils.isBlank(tenantCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            List<MachinePO> poList = machineAccessor.list(tenantCode);
            List<MachineDTO> dtoList = convert(poList);

            return IceMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<MachineDTO>> listByShopCode(String tenantCode, String shopCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(shopCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            List<MachinePO> poList = machineAccessor.listByShopCode(tenantCode, shopCode);
            List<MachineDTO> dtoList = convert(poList);

            return IceMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("listByShopCode|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<MachineDTO> activate(MachineActivatePutRequest request) {
        if (request == null || !request.isValid()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doActivate(request);
        } catch (Exception e) {
            log.error("activate|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> put(MachineUpdatePutRequest request) {
        if (request == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            MachinePO po = convert(request);
            IceMachineResult<Void> result = doPut(po);

            // 异步发送消息准备配置信息分发
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_MACHINE_UPDATED);
            jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, request.getTenantCode());
            jsonPayload.put(CommonConsts.JSON_KEY_MACHINE_CODE, request.getMachineCode());
            asyncDispatcher.dispatch(jsonPayload);

            return result;
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> deleteByMachineCode(String tenantCode, String machineCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(machineCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByMachineCode(tenantCode, machineCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<MachineDTO> doActivate(MachineActivatePutRequest request) {
        // 激活时，设备端是不知道 tenantCode 的，只能通过 deployCode 查找和更新
        DeployPO existDeployPO = deployAccessor.getByDeployCode(request.getDeployCode());
        if (existDeployPO == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        if (!existDeployPO.getMachineCode().equals(request.getMachineCode())) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_DEPLOY_MACHINE_NOT_MATCH));
        }

        existDeployPO.setState(1);
        int updated = deployAccessor.update(existDeployPO);
        if (updated != 1) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }

        MachinePO existMachinePO = machineAccessor.getByMachineCode(existDeployPO.getTenantCode(),
                existDeployPO.getMachineCode());
        if (existMachinePO == null) {
            MachinePO machinePO = convertToMachinePO(request, existDeployPO);
            int inserted = machineAccessor.insert(machinePO);
            if (inserted != 1) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }
            return IceMachineResult.success(convert(machinePO));
        } else {
            return IceMachineResult.success(convert(existMachinePO));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPut(MachinePO po) {
        MachinePO exist = machineAccessor.getByMachineCode(po.getTenantCode(), po.getMachineCode());
        if (exist == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }

        int updated = machineAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        return IceMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doDeleteByMachineCode(String tenantCode, String machineCode) {
        machineAccessor.deleteByMachineCode(tenantCode, machineCode);
        return IceMachineResult.success();
    }

    @Transactional(readOnly = true)
    private IceMachineResult<MachineDTO> doGetByMachineCode(String tenantCode, String machineCode) {
        MachinePO machinePO = machineAccessor.getByMachineCode(tenantCode, machineCode);
        MachineDTO adminRoleDTO = convert(machinePO);
        return IceMachineResult.success(adminRoleDTO);
    }
}
