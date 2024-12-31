package com.langtuo.teamachine.dao.query.user;

import lombok.Data;

@Data
public class AdminQuery {
    /**
     * 管理员登录名称
     */
    private String loginName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
