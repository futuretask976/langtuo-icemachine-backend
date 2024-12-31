package com.langtuo.teamachine.dao.query.rule;

import lombok.Data;

@Data
public class CleanRuleQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 菜单编码
     */
    private String cleanRuleCode;

    /**
     * 菜单名称
     */
    private String cleanRuleName;
}
