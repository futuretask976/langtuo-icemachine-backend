package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AccuracyTplDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     *
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     *
     */
    private String templateCode;

    /**
     *
     */
    private String templateName;

    /**
     * 溢出允许单位，0：固定值，1：百分比
     */
    private int overMode;

    /**
     * 溢出数值
     */
    private int overAmount;

    /**
     * 不及允许单位，0：固定值，1：百分比
     */
    private int underMode;

    /**
     * 不及数值
     */
    private int underAmount;

    /**
     * 应用物料编码列表
     */
    private List<String> toppingCodeList;
}
