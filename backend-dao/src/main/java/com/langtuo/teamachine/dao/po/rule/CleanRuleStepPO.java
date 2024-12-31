package com.langtuo.teamachine.dao.po.rule;

import lombok.Data;

import java.util.Date;

@Data
public class CleanRuleStepPO {
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
     * 清洁规则编码
     */
    private String cleanRuleCode;

    /**
     * 步骤序号，从 1 开始
     */
    private int stepIndex;

    /**
     * 清洗内容，0：冲洗，1：浸泡，2：回料
     */
    private int cleanContent;

    /**
     * 清洗时间（单位：秒）
     */
    private int washSec;

    /**
     * 浸泡时间（单位：分钟）
     */
    private int soakMin;

    /**
     * 浸泡期间冲洗间隔（单位：分钟）
     */
    private int flushIntervalMin;

    /**
     * 浸泡期间冲洗时间（单位：秒）
     */
    private int flushSec;

    /**
     * 回料时间（单位：秒）
     */
    private int recycleSec;

    /**
     * 提醒标题
     */
    private String remindTitle;

    /**
     * 提醒内容
     */
    private String remindContent;

    /**
     * 是否需要再次确认
     */
    private int needConfirm;

    /**
     * 清洗剂类型，0：纯净水，1：消毒水，2：除垢剂，3：空气
     */
    private int cleanAgentType;
}
