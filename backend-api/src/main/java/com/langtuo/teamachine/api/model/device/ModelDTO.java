package com.langtuo.teamachine.api.model.device;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ModelDTO implements Serializable {
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

    /**
     * 管道列表
     */
    private List<ModelPipelineDTO> pipelineList;
}
