package com.langtuo.teamachine.api.service.report;

import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;
import java.util.Map;

public interface SummaryChartService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4TeaAmtByDayChart(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4OrgShopChart(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4DeployChart(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4ShopMachineChart(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4OrderAmtChart(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4TeaAmtChart(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4ToppingAmtChart(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<List<Map<String, String>>> select4SpecItemAmtChart(String tenantCode);
}
