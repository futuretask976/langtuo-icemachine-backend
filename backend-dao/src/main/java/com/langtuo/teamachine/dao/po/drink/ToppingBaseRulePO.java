package com.langtuo.teamachine.dao.po.drink;

import lombok.Data;

import java.util.Date;

@Data
public class ToppingBaseRulePO {
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
     * 茶品编码
     */
    private String teaCode;

    /**
     * 步骤序号，从1开始
     */
    private int stepIndex;

    /**
     * 物料编码
     */
    private String toppingCode;

    /**
     * 基础用量
     */
    private int baseAmount;
}
