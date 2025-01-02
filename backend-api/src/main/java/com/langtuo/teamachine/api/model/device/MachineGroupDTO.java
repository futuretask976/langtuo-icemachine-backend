package com.langtuo.teamachine.api.model.device;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class MachineGroupDTO implements Serializable {
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
    private Map<String, String> extraInfo;

    /**
     * 备注
     */
    private String comment;

    /**
     * 机器分组编码
     */
    private String machineGroupCode;

    /**
     * 机器分组名称
     */
    private String machineGroupName;
}
