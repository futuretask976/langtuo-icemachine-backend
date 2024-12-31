package com.langtuo.teamachine.dao.po.rule;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class DrainRulePO {
    /**
     * 数据表id
     */
    private long id;

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
     * 开业规则编码
     */
    private String drainRuleCode;

    /**
     * 开业规则名称
     */
    private String drainRuleName;

    /**
     * 排空时间
     */
    private int flushSec;
}
