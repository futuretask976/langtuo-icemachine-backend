package com.langtuo.teamachine.dao.accessor.user;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.constant.PermitActEnum;
import com.langtuo.teamachine.dao.mapper.user.RoleActRelMapper;
import com.langtuo.teamachine.dao.po.user.RoleActRelPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RoleActRelAccessor {
    @Resource
    private RoleActRelMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<RoleActRelPO> listByRoleCode(String tenantCode, String roleCode) {
        // 超级管理员特殊逻辑
        List<RoleActRelPO> superRoleActRelPOList = getSysSuperRoleActRel(tenantCode, roleCode);
        if (superRoleActRelPOList != null) {
            return superRoleActRelPOList;
        }

        // 首先访问缓存
        List<RoleActRelPO> cachedList = getCacheList(tenantCode, roleCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<RoleActRelPO> list = mapper.selectList(tenantCode, roleCode);

        // 设置缓存
        setCacheList(tenantCode, roleCode, list);
        return list;
    }

    public int insert(RoleActRelPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheList(po.getTenantCode(), po.getRoleCode());
        }
        return inserted;
    }

    public int deleteByRoleCode(String tenantCode, String roleCode) {
        int deleted = mapper.delete(tenantCode, roleCode);
        if (deleted > CommonConsts.DB_DELETED_ZERO_ROW) {
            deleteCacheList(tenantCode, roleCode);
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String roleCode) {
        return "roleActRelAcc-" + tenantCode + "-" + roleCode;
    }

    private List<RoleActRelPO> getCacheList(String tenantCode, String roleCode) {
        String key = getCacheListKey(tenantCode, roleCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<RoleActRelPO> poList = (List<RoleActRelPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String roleCode, List<RoleActRelPO> poList) {
        String key = getCacheListKey(tenantCode, roleCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String roleCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, roleCode));
    }

    public List<RoleActRelPO> getSysSuperRoleActRel(String tenantCode, String roleCode) {
        if (!CommonConsts.ROLE_CODE_SYS_SUPER.equals(roleCode)) {
            return null;
        }

        List<RoleActRelPO> roleActRelPOList = Lists.newArrayList();
        for (PermitActEnum permitActEnum : PermitActEnum.values()) {
            RoleActRelPO roleActRelPO = new RoleActRelPO();
            roleActRelPO.setTenantCode(tenantCode);
            roleActRelPO.setRoleCode(roleCode);
            roleActRelPO.setPermitActCode(permitActEnum.getPermitActCode());
            roleActRelPOList.add(roleActRelPO);
        }

        RoleActRelPO roleActRelPO4Tenant = new RoleActRelPO();
        roleActRelPO4Tenant.setTenantCode(tenantCode);
        roleActRelPO4Tenant.setRoleCode(roleCode);
        roleActRelPO4Tenant.setPermitActCode(CommonConsts.PERMIT_ACT_CODE_TENANT);
        roleActRelPOList.add(roleActRelPO4Tenant);

        RoleActRelPO roleActRelPO4AndroidApp = new RoleActRelPO();
        roleActRelPO4AndroidApp.setTenantCode(tenantCode);
        roleActRelPO4AndroidApp.setRoleCode(roleCode);
        roleActRelPO4AndroidApp.setPermitActCode(CommonConsts.PERMIT_ACT_CODE_ANDROID_APP_MGT);
        roleActRelPOList.add(roleActRelPO4AndroidApp);

        return roleActRelPOList;
    }
}
