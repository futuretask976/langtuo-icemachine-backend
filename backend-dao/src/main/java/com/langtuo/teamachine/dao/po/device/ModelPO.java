package com.langtuo.teamachine.dao.po.device;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ModelPO {
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
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 是否支持同时出料，0：不支持，1：支持
     */
    private int enableFlowAll;

    /**
     * 管道同时清洗的最大数量
     */
    private int pipeCleanSimultMaxCnt;
}
