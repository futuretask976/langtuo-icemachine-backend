package com.langtuo.teamachine.web.controller.report;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.report.OrderReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderSpecItemReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderTeaReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderToppingReportByDayDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.report.OrderReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/reportset/order")
public class OrderReportController {
    @Resource
    private OrderReportService orderReportService;

    @GetMapping(value = "/calc")
    public TeaMachineResult<Void> calc(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("orderCreatedDay") String orderCreatedDay) {
        TeaMachineResult<Void> rtn = orderReportService.calc(tenantCode, orderCreatedDay);
        return rtn;
    }

    @GetMapping(value = "/orderreport/search")
    public TeaMachineResult<PageDTO<OrderReportByDayDTO>> searchAmtReport(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name="orderCreatedDay", required = false) String orderCreatedDay,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrderReportByDayDTO>> rtn = orderReportService.searchOrderReport(tenantCode,
                orderCreatedDay, pageNum, pageSize);
        return rtn;
    }

    @GetMapping(value = "/teareport/search")
    public TeaMachineResult<PageDTO<OrderTeaReportByDayDTO>> searchTeaReportReport(
            @RequestParam("tenantCode") String tenantCode,
            @RequestParam(name="orderCreatedDay", required = false) String orderCreatedDay,
            @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrderTeaReportByDayDTO>> rtn = orderReportService.searchTeaReport(tenantCode,
                orderCreatedDay, shopGroupCode, shopCode, pageNum, pageSize);
        return rtn;
    }

    @GetMapping(value = "/specitemreport/search")
    public TeaMachineResult<PageDTO<OrderSpecItemReportByDayDTO>> searchSpecItemReportReport(
            @RequestParam("tenantCode") String tenantCode,
            @RequestParam(name="orderCreatedDay", required = false) String orderCreatedDay,
            @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrderSpecItemReportByDayDTO>> rtn =
                orderReportService.searchSpecItemReport(tenantCode, orderCreatedDay, shopGroupCode, shopCode,
                        pageNum, pageSize);
        return rtn;
    }

    @GetMapping(value = "/toppingreport/search")
    public TeaMachineResult<PageDTO<OrderToppingReportByDayDTO>> searchToppingReportReport(
            @RequestParam("tenantCode") String tenantCode,
            @RequestParam(name="orderCreatedDay", required = false) String orderCreatedDay,
            @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrderToppingReportByDayDTO>> rtn =
                orderReportService.searchToppingReport(tenantCode, orderCreatedDay, shopGroupCode, shopCode,
                        pageNum, pageSize);
        return rtn;
    }
}
