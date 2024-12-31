package com.langtuo.teamachine.dao.po.device;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class MachineGroupPO {
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
    private Map<String, String> extraInfo;

    /**
     * 备注
     */
    private String comment;

    /**
     * 分组编码
     */
    private String machineGroupCode;

    /**
     * 分组名称
     */
    private String machineGroupName;
}
