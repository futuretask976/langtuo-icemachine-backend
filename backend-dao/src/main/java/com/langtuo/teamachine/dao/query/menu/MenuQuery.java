package com.langtuo.teamachine.dao.query.menu;

import lombok.Data;

@Data
public class MenuQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;
}
