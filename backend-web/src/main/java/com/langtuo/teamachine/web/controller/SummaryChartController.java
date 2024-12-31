package com.langtuo.teamachine.web.controller;

import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.report.SummaryChartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/summarychart")
public class SummaryChartController {
    @Resource
    private SummaryChartService summaryChartService;

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/teaamtbyday?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/teaamtbyday")
    public TeaMachineResult<List<Map<String, String>>> select4TeaAmtByDayChart(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4TeaAmtByDayChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/orgshop?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/orgshop")
    public TeaMachineResult<List<Map<String, String>>> orgShop(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4OrgShopChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/deploy?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/deploy")
    public TeaMachineResult<List<Map<String, String>>> deploy(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4DeployChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/shopmachine?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/shopmachine")
    public TeaMachineResult<List<Map<String, String>>> shopMachine(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4ShopMachineChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/orderamt?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/orderamt")
    public TeaMachineResult<List<Map<String, String>>> orderAmt(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4OrderAmtChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/teaamt?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/teaamt")
    public TeaMachineResult<List<Map<String, String>>> teaAmt(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4TeaAmtChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/toppingamt?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/toppingamt")
    public TeaMachineResult<List<Map<String, String>>> toppingAmt(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4ToppingAmtChart(tenantCode);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/summarychart/specitemamt?tenantCode=tenant_001
     * @param tenantCode
     * @return
     */
    @GetMapping(value = "/specitemamt")
    public TeaMachineResult<List<Map<String, String>>> specItemAmt(@RequestParam(name = "tenantCode") String tenantCode) {
        TeaMachineResult<List<Map<String, String>>> rtn = summaryChartService.select4SpecItemAmtChart(tenantCode);
        return rtn;
    }
}
