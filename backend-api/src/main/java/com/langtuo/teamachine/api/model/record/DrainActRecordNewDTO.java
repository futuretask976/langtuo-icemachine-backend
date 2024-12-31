package com.langtuo.teamachine.api.model.record;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class DrainActRecordNewDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

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
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 店铺组名称
     */
    private String shopGroupName;

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
     * 物料名称
     */
    private String toppingName;

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
