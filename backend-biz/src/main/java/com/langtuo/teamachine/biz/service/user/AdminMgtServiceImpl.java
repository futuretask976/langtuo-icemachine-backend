package com.langtuo.teamachine.biz.service.user;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.request.user.AdminPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.user.AdminMgtService;
import com.langtuo.teamachine.biz.manager.AdminManager;
import com.langtuo.teamachine.biz.manager.OrgManager;
import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.po.user.AdminPO;
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

import static com.langtuo.teamachine.biz.convertor.user.AdminMgtConvertor.convertToAdminDTO;
import static com.langtuo.teamachine.biz.convertor.user.AdminMgtConvertor.convertToAdminPO;

@Component
@Slf4j
public class AdminMgtServiceImpl implements AdminMgtService {
    @Resource
    private AdminManager adminManager;

    @Resource
    private OrgManager orgManager;

    @Resource
    private AdminAccessor adminAccessor;

    @Resource
    private RoleAccessor roleAccessor;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<AdminDTO> getByLoginName(String tenantCode, String loginName) {
        AdminPO adminPO = adminAccessor.getByLoginName(tenantCode, loginName);
        return IceMachineResult.success(convertToAdminDTO(adminPO));
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<AdminDTO>> search(String tenantCode, String loginName, String roleCode,
                                                      int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        IceMachineResult<PageDTO<AdminDTO>> iceMachineResult;
        try {
            List<String> orgNameList = orgManager.getOrgNameListByLoginSession(tenantCode);
            PageInfo<AdminPO> pageInfo = adminAccessor.search(tenantCode, loginName, roleCode, orgNameList,
                    pageNum, pageSize);
            iceMachineResult = IceMachineResult.success(new PageDTO<>(convertToAdminDTO(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return iceMachineResult;
    }

    //@Override
    //@Transactional(readOnly = true)
    //public TeaMachineResult<List<AdminDTO>> list(String tenantCode) {
    //    try {
    //        List<AdminPO> list = adminAccessor.list(tenantCode);
    //        return TeaMachineResult.success(convertToAdminDTO(list));
    //    } catch (Exception e) {
    //        log.error("list|fatal|e=" + e.getMessage(), e);
    //        return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
    //    }
    //}

    @Override
    public IceMachineResult<Void> put(AdminPutRequest request) {
        if (request == null || !request.isValid()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        AdminPO po = convertToAdminPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(po);
            } else {
                return doPutUpdate(po);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public IceMachineResult<Void> deleteByLoginName(String tenantCode, String loginName) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(loginName)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        IceMachineResult<Void> iceMachineResult;
        try {
            adminAccessor.deleteByLoginName(tenantCode, loginName);
            iceMachineResult = IceMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return iceMachineResult;
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<Integer> countByRoleCode(String tenantCode, String roleCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(roleCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        IceMachineResult<Integer> iceMachineResult;
        try {
            int cnt = adminAccessor.countByRoleCode(tenantCode, roleCode);
            iceMachineResult = IceMachineResult.success(cnt);
        } catch (Exception e) {
            log.error("countByRoleCode|fatal|e=" + e.getMessage(), e);
            iceMachineResult = IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return iceMachineResult;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutNew(AdminPO po) {
        AdminPO exist = adminAccessor.getByLoginName(po.getTenantCode(), po.getLoginName());
        if (exist != null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = adminAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNew|error|inserted=" + inserted);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return IceMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutUpdate(AdminPO po) {
        AdminPO exist = adminAccessor.getByLoginName(po.getTenantCode(), po.getLoginName());
        if (exist == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        if (CommonConsts.ROLE_CODE_TENANT_SUPER.equals(exist.getRoleCode())) {
            AdminPO loginAdminPO = adminManager.getAdminPOByLoginSession(po.getTenantCode());
            if (!CommonConsts.ROLE_CODE_SYS_SUPER.equals(loginAdminPO.getRoleCode())) {
                return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(
                        ErrorCodeEnum.BIZ_ERR_CANNOT_MODIFY_TENANT_SUPER_ADMIN));
            }
        }

        int updated = adminAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdate|error|updated=" + updated);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        return IceMachineResult.success();
    }
}
