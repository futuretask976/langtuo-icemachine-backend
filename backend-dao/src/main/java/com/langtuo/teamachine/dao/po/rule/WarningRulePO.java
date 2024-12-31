package com.langtuo.teamachine.dao.po.rule;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class WarningRulePO {
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
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 预警规则编码
     */
    private String warningRuleCode;

    /**
     * 预警规则名称
     */
    private String warningRuleName;

    /**
     * 预警内容，0：报废预警，1：缺料预警，2：清洗预警
     */
    private int warningContent;

    /**
     * 预警类型，0：弱提醒，1：强提醒
     */
    private int warningType;

    /**
     * 阈值类型，0：绝对值，1：百分比
     */
    private int thresholdMode;

    /**
     * 阈值
     */
    private int threshold;
}
