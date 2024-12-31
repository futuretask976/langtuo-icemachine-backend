package com.langtuo.teamachine.dao.po.device;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class MachinePO {
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
     * 机器编码
     */
    private String machineCode;

    /**
     * 机器名称
     */
    private String machineName;

    /**
     * 分组编码
     */
    private String machineGroupCode;

    /**
     * 状态，0：禁用，1：启用
     */
    private int state;

    /**
     * 在线状态，0：离线，1：在线
     */
    private int onlineState;
}
