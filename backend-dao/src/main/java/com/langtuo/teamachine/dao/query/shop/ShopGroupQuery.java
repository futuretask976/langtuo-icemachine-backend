package com.langtuo.teamachine.dao.query.shop;

import lombok.Data;

import java.util.List;

@Data
public class ShopGroupQuery {
    /**
     * 店铺组名称
     */
    private String shopGroupName;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 组织结构名称列表
     */
    private List<String> orgNameList;
}
