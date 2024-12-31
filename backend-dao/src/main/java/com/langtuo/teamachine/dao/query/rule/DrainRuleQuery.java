package com.langtuo.teamachine.dao.query.rule;

import lombok.Data;

@Data
public class DrainRuleQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 菜单编码
     */
    private String drainRuleCode;

    /**
     * 菜单名称
     */
    private String drainRuleName;
}
