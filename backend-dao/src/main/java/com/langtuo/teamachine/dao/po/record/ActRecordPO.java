package com.langtuo.teamachine.dao.po.record;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ActRecordPO {
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
    private Map<String,String> extraInfo;

    /**
     * 幂等标记
     */
    private String idempotentMark;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 清洗开始时间
     */
    private Date drainStartTime;

    /**
     * 清洗结束时间
     */
    private Date drainEndTime;

    /**
     * 物料名称
     */
    private String toppingCode;

    /**
     * 管道序号
     */
    private int pipelineNum;

    /**
     * 清洗方式，0：排空规则排空，1：手动排空
     */
    private int drainType;

    /**
     * 开业规则编码
     */
    private String drainRuleCode;

    /**
     * 排空时间（单位：秒）
     */
    private int flushSec;
}