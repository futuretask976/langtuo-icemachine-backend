package com.langtuo.teamachine.dao.po.menu;

import lombok.Data;

import java.util.Date;

@Data
public class MenuDispatchCachePO {
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
     * 是否初始化触发，0：不是，1：是
     */
    private int init;

    /**
     * OSS 文件名称
     */
    private String fileName;

    /**
     * md5 编码
     */
    private String md5;
}
