package com.langtuo.teamachine.dao.query.user;

import lombok.Data;

@Data
public class AdminRoleQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 角色名称
     */
    private String roleName;
}
