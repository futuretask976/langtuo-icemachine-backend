package com.langtuo.teamachine.api.service.report;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.report.OrderReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderSpecItemReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderTeaReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderToppingReportByDayDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;

public interface OrderReportService {
    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    TeaMachineResult<Void> calc(String tenantCode, String orderCreatedDay);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @param pageNum
     * @param pageSize
     * @return
     */
    TeaMachineResult<PageDTO<OrderReportByDayDTO>> searchOrderReport(String tenantCode, String orderCreatedDay,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @param shopGroupCode
     * @param shopCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    TeaMachineResult<PageDTO<OrderTeaReportByDayDTO>> searchTeaReport(String tenantCode, String orderCreatedDay,
            String shopGroupCode, String shopCode, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @param shopGroupCode
     * @param shopCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    TeaMachineResult<PageDTO<OrderSpecItemReportByDayDTO>> searchSpecItemReport(String tenantCode, String orderCreatedDay,
            String shopGroupCode, String shopCode, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @param shopGroupCode
     * @param shopCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    TeaMachineResult<PageDTO<OrderToppingReportByDayDTO>> searchToppingReport(String tenantCode, String orderCreatedDay,
            String shopGroupCode, String shopCode, int pageNum, int pageSize);
}
