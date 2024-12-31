package com.langtuo.teamachine.dao.query.shop;

import lombok.Data;

import java.util.List;

@Data
public class ShopQuery {
    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 组织架构列表
     */
    private List<String> shopGroupCodeList;
}
