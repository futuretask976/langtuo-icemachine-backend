package com.langtuo.teamachine.dao.query.drink;

import lombok.Data;

@Data
public class TeaQuery {
    /**
     * 茶品编码
     */
    private String teaCode;

    /**
     * 茶品名称
     */
    private String teaName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
