package com.langtuo.teamachine.dao.query.drink;

import lombok.Data;

@Data
public class TeaTypeQuery {
    /**
     * 茶品类型编码
     */
    private String teaTypeCode;

    /**
     * 茶品类型名称
     */
    private String teaTypeName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
