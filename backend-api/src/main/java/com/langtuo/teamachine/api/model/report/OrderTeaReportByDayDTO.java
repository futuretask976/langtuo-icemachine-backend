package com.langtuo.teamachine.api.model.report;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderTeaReportByDayDTO  implements Serializable {
    /**
     * 订单创建时间，例如 2024-09-20
     */
    private String orderCreatedDay;

    /**
     * 茶品编码
     */
    private String teaCode;

    /**
     * 数量
     */
    private int amount;
}
