package com.langtuo.teamachine.dao.query.rule;

import lombok.Data;

@Data
public class ConfigRuleQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 配置规则编码
     */
    private String configRuleCode;

    /**
     * 配置规则名称
     */
    private String configRuleName;
}
