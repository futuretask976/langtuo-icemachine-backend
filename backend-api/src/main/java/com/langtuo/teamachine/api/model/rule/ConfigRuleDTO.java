package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class ConfigRuleDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 备注
     */
    private String comment;

    /**
     * 配置规则编码
     */
    private String configRuleCode;

    /**
     * 配置规则名称
     */
    private String configRuleName;
}
