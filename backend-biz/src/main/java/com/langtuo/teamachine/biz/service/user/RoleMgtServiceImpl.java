package com.langtuo.teamachine.biz.service.user;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.user.RoleDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.RolePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.user.RoleMgtService;
import com.langtuo.teamachine.biz.convertor.user.RoleMgtConvertor;
import com.langtuo.teamachine.biz.manager.AdminManager;
import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.accessor.user.PermitActAccessor;
import com.langtuo.teamachine.dao.accessor.user.RoleAccessor;
import com.langtuo.teamachine.dao.accessor.user.RoleActRelAccessor;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import com.langtuo.teamachine.dao.po.user.RoleActRelPO;
import com.langtuo.teamachine.dao.po.user.RolePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.langtuo.teamachine.biz.convertor.user.RoleMgtConvertor.*;

@Component
@Slf4j
public class RoleMgtServiceImpl implements RoleMgtService {
    @Resource
    private AdminManager adminManager;

    @Resource
    private RoleAccessor roleAccessor;

    @Resource
    private RoleActRelAccessor roleActRelAccessor;

    @Resource
    private PermitActAccessor permitActAccessor;

    @Resource
    private AdminAccessor adminAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<RoleDTO> getByCode(String tenantCode, String roleCode) {
        RolePO rolePO = roleAccessor.getByRoleCode(tenantCode, roleCode);
        RoleDTO roleDTO = RoleMgtConvertor.convertToRoleDTO(rolePO);
        return TeaMachineResult.success(roleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<RoleDTO>> search(String tenantCode, String roleName, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<RolePO> pageInfo = roleAccessor.search(tenantCode, roleName, pageNum, pageSize);
            return TeaMachineResult.success(new PageDTO<>(convertToRoleDTO(pageInfo.getList()), pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<RoleDTO>> list(String tenantCode) {
        TeaMachineResult<List<RoleDTO>> teaMachineResult;
        try {
            List<RolePO> list = roleAccessor.search(tenantCode);
            if (!CollectionUtils.isEmpty(list)) {
                list = list.stream().filter(rolePO -> {
                    if (CommonConsts.ROLE_CODE_SYS_SUPER.equals(rolePO.getRoleCode())) {
                        return false;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());
            }

            List<RoleDTO> dtoList = convertToRoleDTO(list);
            teaMachineResult = TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            teaMachineResult = TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return teaMachineResult;
    }

    @Override
    public TeaMachineResult<Void> put(RolePutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        RolePO rolePO = convertToRolePO(request);
        List<RoleActRelPO> roleActRelPOList = convertRoleActRel(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(rolePO, roleActRelPOList);
            } else {
                return doPutUpdate(rolePO, roleActRelPOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(RolePO po, List<RoleActRelPO> actRelPOList) {
        RolePO exist = roleAccessor.getByRoleCode(po.getTenantCode(), po.getRoleCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = roleAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNewRole|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        roleActRelAccessor.deleteByRoleCode(po.getTenantCode(), po.getRoleCode());
        for (RoleActRelPO actRelPO : actRelPOList) {
            int inserted4actRel = roleActRelAccessor.insert(actRelPO);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                log.error("putNewActRel|error|" + inserted4actRel);
            }
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(RolePO po, List<RoleActRelPO> actRelPOList) {
        RolePO exist = roleAccessor.getByRoleCode(po.getTenantCode(), po.getRoleCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        if (CommonConsts.ROLE_CODE_TENANT_SUPER.equals(po.getRoleCode())) {
            AdminPO adminPO = adminManager.getAdminPOByLoginSession(po.getTenantCode());
            if (!CommonConsts.ROLE_CODE_SYS_SUPER.equals(adminPO.getRoleCode())) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                        ErrorCodeEnum.BIZ_ERR_CANNOT_MODIFY_TENANT_SUPER_ADMIN_ROLE));
            }
        }

        int updated = roleAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdateRole|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        roleActRelAccessor.deleteByRoleCode(po.getTenantCode(), po.getRoleCode());
        for (RoleActRelPO actRelPO : actRelPOList) {
            int inserted4actRel = roleActRelAccessor.insert(actRelPO);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4actRel) {
                log.error("putUpdateActRel|error|" + inserted4actRel);
            }
        }
        return TeaMachineResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Void> delete(String tenantCode, String roleCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(roleCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        if (CommonConsts.ROLE_CODE_TENANT_SUPER.equals(roleCode)) {
            if (CommonConsts.ROLE_CODE_TENANT_SUPER.equals(roleCode)) {
                AdminPO adminPO = adminManager.getAdminPOByLoginSession(tenantCode);
                if (!CommonConsts.ROLE_CODE_SYS_SUPER.equals(adminPO.getRoleCode())) {
                    return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                            ErrorCodeEnum.BIZ_ERR_CANNOT_MODIFY_TENANT_SUPER_ADMIN_ROLE));
                }
            }
        }

        try {
            int adminCount = adminAccessor.countByRoleCode(tenantCode, roleCode);
            if (adminCount > 0) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                        ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
            }

            roleAccessor.deleteByRoleCode(tenantCode, roleCode);
            roleActRelAccessor.deleteByRoleCode(tenantCode, roleCode);
            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}
