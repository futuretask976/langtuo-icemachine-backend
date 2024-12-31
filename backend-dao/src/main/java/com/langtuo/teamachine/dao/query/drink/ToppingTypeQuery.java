package com.langtuo.teamachine.dao.query.drink;

import lombok.Data;

@Data
public class ToppingTypeQuery {
    /**
     * 物料类型编码
     */
    private String toppingTypeCode;

    /**
     * 物料类型名称
     */
    private String toppingTypeName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
