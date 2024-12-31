package com.langtuo.teamachine.biz.service.user;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.biz.aync.AsyncDispatcher;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.user.TenantMgtService;
import com.langtuo.teamachine.biz.convertor.user.TenantMgtConvertor;
import com.langtuo.teamachine.dao.accessor.user.TenantAccessor;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.user.TenantMgtConvertor.*;

@Component
@Slf4j
public class TenantMgtServiceImpl implements TenantMgtService {
    @Resource
    private TenantAccessor tenantAccessor;

    @Resource
    private AsyncDispatcher asyncDispatcher;

    @Override
    public IceMachineResult<List<TenantDTO>> list() {
        IceMachineResult<List<TenantDTO>> iceMachineResult;
        try {
            List<TenantPO> list = tenantAccessor.list();
            List<TenantDTO> dtoList = TenantMgtConvertor.convertToTenantDTO(list);
            iceMachineResult = IceMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("tenantMgtService|list|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return iceMachineResult;
    }

    @Override
    public IceMachineResult<PageDTO<TenantDTO>> search(String tenantName, String contactPerson,
                                                       int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        IceMachineResult<PageDTO<TenantDTO>> iceMachineResult;
        try {
            PageInfo<TenantPO> pageInfo = tenantAccessor.search(tenantName, contactPerson, pageNum, pageSize);
            List<TenantDTO> dtoList = TenantMgtConvertor.convertToTenantDTO(pageInfo.getList());
            iceMachineResult = IceMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("tenantMgtService|search|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return iceMachineResult;
    }

    @Override
    public IceMachineResult<TenantDTO> get(String tenantCode) {
        IceMachineResult<TenantDTO> iceMachineResult;
        try {
            TenantPO po = tenantAccessor.selectOneByTenantCode(tenantCode);
            TenantDTO dto = convertToTenantDTO(po);
            iceMachineResult = IceMachineResult.success(dto);
        } catch (Exception e) {
            log.error("tenantMgtService|get|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return iceMachineResult;
    }

    @Override
    public IceMachineResult<Void> put(TenantPutRequest request) {
        if (request == null || !request.isValid()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        TenantPO po = convertToTenantPO(request);
        IceMachineResult<Void> iceMachineResult;
        if (request.isPutNew()) {
            iceMachineResult = putNew(po);
        } else {
            iceMachineResult = putUpdate(po);
        }

        // 异步发送消息准备添加超级租户管理角色和超级租户管理员
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_TENANT_UPDATED);
        jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, request.getTenantCode());
        asyncDispatcher.dispatch(jsonPayload);

        return iceMachineResult;
    }

    private IceMachineResult<Void> putNew(TenantPO po) {
        try {
            TenantPO exist = tenantAccessor.selectOneByTenantCode(po.getTenantCode());
            if (exist != null) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
            }

            int inserted = tenantAccessor.insert(po);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                log.error("tenantMgtService|putNew|error|" + inserted);
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("tenantMgtService|putNew|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    private IceMachineResult<Void> putUpdate(TenantPO po) {
        try {
            TenantPO exist = tenantAccessor.selectOneByTenantCode(po.getTenantCode());
            if (exist == null) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
            }

            int updated = tenantAccessor.update(po);
            if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
                log.error("tenantMgtService|putUpdate|error|" + updated);
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
            }
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("tenantMgtService|putUpdate|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> delete(String tenantCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        IceMachineResult<Void> iceMachineResult;
        try {
            tenantAccessor.deleteByTenantCode(tenantCode);
            iceMachineResult = IceMachineResult.success();
        } catch (Exception e) {
            log.error("tenantMgtService|delete|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return iceMachineResult;
    }
}
