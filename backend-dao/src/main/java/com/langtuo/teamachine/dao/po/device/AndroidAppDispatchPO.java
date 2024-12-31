package com.langtuo.teamachine.dao.po.device;

import lombok.Data;

import java.util.Date;

@Data
public class AndroidAppDispatchPO {
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
     * 版本号
     */
    private String version;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;
}
