package com.langtuo.teamachine.dao.po.device;

import lombok.Data;

import java.util.Date;

@Data
public class ModelPipelinePO {
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
     * 型号编码
     */
    private String modelCode;

    /**
     * 管道序号
     */
    private int pipelineNum;

    /**
     * 是否支持加热，0：不支持，1：支持
     */
    private int enableWarm;

    /**
     * 是否支持冷藏，0：不支持，1：支持
     */
    private int enableFreeze;

    /**
     * 容量
     */
    private int capacity;

    /**
     * 是否是糖浆桶，0：不是，1：是
     */
    private int syrupPipeline;

    /**
     * 传感器阈值
     */
    private int sensorThreshold;
}
