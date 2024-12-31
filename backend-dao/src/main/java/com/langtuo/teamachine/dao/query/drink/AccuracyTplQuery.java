package com.langtuo.teamachine.dao.query.drink;

import lombok.Data;

@Data
public class AccuracyTplQuery {
    /**
     * 规格编码
     */
    private String templateCode;

    /**
     * 规格名称
     */
    private String templateName;

    /**
     * 租户编码
     */
    private String tenantCode;
}
