package com.langtuo.teamachine.biz.service.device;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.ShopGroupDTO;
import com.langtuo.teamachine.api.request.device.ShopGroupPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.device.ShopGroupMgtService;
import com.langtuo.teamachine.biz.manager.OrgManager;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
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

import static com.langtuo.teamachine.biz.convertor.device.ShopGroupMgtConvertor.convert;

@Component
@Slf4j
public class ShopGroupMgtServiceImpl implements ShopGroupMgtService {
    @Resource
    private OrgManager orgManager;

    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    @Resource
    private ShopAccessor shopAccessor;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<ShopGroupDTO> getByShopGroupCode(String tenantCode, String shopGroupCode) {
        MachineGroupPO machineGroupPO = machineGroupAccessor.getByShopGroupCode(tenantCode, shopGroupCode);
        ShopGroupDTO shopGroupDTO = convert(machineGroupPO);
        return IceMachineResult.success(shopGroupDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<ShopGroupDTO>> search(String tenantCode, String shopGroupName,
                                                          int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            List<String> orgNameList = orgManager.getOrgNameListByLoginSession(tenantCode);
            PageInfo<MachineGroupPO> pageInfo = machineGroupAccessor.search(tenantCode, shopGroupName,
                    pageNum, pageSize, orgNameList);

            return IceMachineResult.success(new PageDTO<>(
                    convert(pageInfo.getList()), pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<ShopGroupDTO>> list(String tenantCode) {
        try {
            List<String> orgNameList = orgManager.getOrgNameListByLoginSession(tenantCode);
            List<MachineGroupPO> list = machineGroupAccessor.list(tenantCode, orgNameList);
            return IceMachineResult.success(convert(list));
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> put(ShopGroupPutRequest request) {
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
            MachineGroupPO exist = machineGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getMachineGroupCode());
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
            MachineGroupPO exist = machineGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getMachineGroupCode());
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
    public IceMachineResult<Void> deleteByShopGroupCode(String tenantCode, String shopGroupCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(shopGroupCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            int count = shopAccessor.countByShopGroupCode(tenantCode, shopGroupCode);
            if (count == CommonConsts.DB_SELECT_ZERO_ROW) {
                machineGroupAccessor.deleteByShopGroupCode(tenantCode, shopGroupCode);
                return IceMachineResult.success();
            } else {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(
                        ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
            }
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}
