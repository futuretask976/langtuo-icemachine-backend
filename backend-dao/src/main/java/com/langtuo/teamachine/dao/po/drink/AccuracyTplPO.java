package com.langtuo.teamachine.dao.po.drink;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class AccuracyTplPO {
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
     * 注释
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 溢出允许模式，0：固定值，1：百分比
     */
    private int overMode;

    /**
     * 溢出数值
     */
    private int overAmount;

    /**
     * 不及允许模式，0：固定值，1：百分比
     */
    private int underMode;

    /**
     * 不及数值
     */
    private int underAmount;
}
