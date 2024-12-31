package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrainRuleToppingDTO implements Serializable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 清洁规则编码
     */
    private String drainRuleCode;

    /**
     * 清洁排除物料编码
     */
    private String toppingCode;

    /**
     * 清洁排除物料编码
     */
    private String toppingName;

    /**
     * 排空时间（单位：秒）
     */
    private int flushSec;
}
