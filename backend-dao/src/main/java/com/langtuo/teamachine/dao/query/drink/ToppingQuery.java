package com.langtuo.teamachine.dao.query.drink;

import lombok.Data;

@Data
public class ToppingQuery {
    /**
     * 物料编码
     */
    private String toppingCode;

    /**
     * 物料名称
     */
    private String toppingName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
