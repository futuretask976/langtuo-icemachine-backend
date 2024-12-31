package com.langtuo.teamachine.dao.po.menu;

import lombok.Data;

import java.util.Date;

@Data
public class MenuSeriesRelPO {
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
     * 菜单编码
     */
    private String menuCode;

    /**
     * 系列编码
     */
    private String seriesCode;
}
