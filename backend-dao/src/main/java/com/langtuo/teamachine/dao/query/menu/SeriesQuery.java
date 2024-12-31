package com.langtuo.teamachine.dao.query.menu;

import lombok.Data;

@Data
public class SeriesQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 物料编码
     */
    private String seriesCode;

    /**
     * 物料名称
     */
    private String seriesName;
}
