package com.langtuo.teamachine.api.model.record;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class ActRecordDTO implements Serializable  {
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
     * 额外信息
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
     * 机器分组编码
     */
    private String machineGroupCode;
}
