package com.langtuo.teamachine.dao.query.user;

import lombok.Data;

import java.util.List;

@Data
public class AdminQuery {
    /**
     * 管理员登录名称
     */
    private String loginName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 组织结构列表
     */
    private List<String> orgNameList;
}
