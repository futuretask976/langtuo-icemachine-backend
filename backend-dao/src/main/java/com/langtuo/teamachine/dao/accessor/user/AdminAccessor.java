package com.langtuo.teamachine.dao.accessor.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.user.AdminMapper;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import com.langtuo.teamachine.dao.query.user.AdminQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AdminAccessor {
    @Resource
    private AdminMapper mapper;

    public AdminPO getByLoginName(String tenantCode, String loginName) {
        // 超级管理员特殊逻辑
        AdminPO superAdminPO = getSysSuperAdmin(tenantCode, loginName);
        if (superAdminPO != null) {
            return superAdminPO;
        }

        AdminPO po = mapper.selectOne(tenantCode, loginName);
        return po;
    }

    public List<AdminPO> list(String tenantCode) {
        List<AdminPO> list = mapper.selectList(tenantCode);
        return list;
    }

    public PageInfo<AdminPO> search(String tenantCode, String loginName, String roleCode, List<String> orgNameList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setTenantCode(tenantCode);
        adminQuery.setLoginName(StringUtils.isBlank(loginName) ? null : loginName);
        List<AdminPO> list = mapper.search(adminQuery);

        PageInfo<AdminPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(AdminPO po) {
        int inserted = mapper.insert(po);
        return inserted;
    }

    public int update(AdminPO po) {
        AdminPO exist = mapper.selectOne(po.getTenantCode(), po.getLoginName());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        return updated;
    }

    public int deleteByLoginName(String tenantCode, String loginName) {
        AdminPO po = getByLoginName(tenantCode, loginName);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, loginName);
        return deleted;
    }

    private AdminPO getSysSuperAdmin(String tenantCode, String loginName) {
        if (!CommonConsts.ADMIN_SYS_SUPER_LOGIN_NAME.equals(loginName)) {
            return null;
        }

        AdminPO po = new AdminPO();
        po.setTenantCode(tenantCode);
        po.setLoginName(CommonConsts.ADMIN_SYS_SUPER_LOGIN_NAME);
        po.setLoginPass(CommonConsts.ADMIN_SYS_SUPER_PASSWORD);
        return po;
    }
}
