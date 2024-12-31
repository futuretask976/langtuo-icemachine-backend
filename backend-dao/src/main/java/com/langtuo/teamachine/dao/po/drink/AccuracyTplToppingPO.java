package com.langtuo.teamachine.dao.po.drink;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class AccuracyTplToppingPO {
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
     * 模板编码
     */
    private String templateCode;

    /**
     * 物料编码
     */
    private String toppingCode;
}
