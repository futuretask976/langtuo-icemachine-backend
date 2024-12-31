package com.langtuo.teamachine.api.model.report;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSpecItemReportByDayDTO implements Serializable  {
    /**
     * 订单创建时间，例如 2024-09-20
     */
    private String orderCreatedDay;

    /**
     * 规格项编码
     */
    private String specItemCode;

    /**
     * 数量
     */
    private int amount;
}
