package com.langtuo.teamachine.dao.accessor;

import com.langtuo.teamachine.dao.mapper.SummaryChartMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SummaryChartAccessor {
    @Resource
    private SummaryChartMapper summaryChartMapper;

    /**
     *
     * @param tenantCode
     * @return
     */
    public List<Map<String, String>> select4TeaAmtByDayChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4TeaAmtByDayChart(tenantCode);
    }

    /**
     *
     * @param tenantCode
     * @return
     */
    public List<Map<String, String>> select4OrgShopChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4OrgShopChart(tenantCode);
    }

    /**
     *
     * @param tenantCode
     * @return
     */
    public List<Map<String, String>> select4DeployChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4DeployChart(tenantCode);
    }

    /**
     *
     * @param tenantCode
     * @return
     */
    public List<Map<String, String>> select4ShopMachineChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4ShopMachineChart(tenantCode);
    }

    /**
     *
     * @return
     */
    public List<Map<String, String>> select4OrderAmtChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4OrderAmtChart(tenantCode);
    }

    /**
     *
     * @return
     */
    public List<Map<String, String>> select4TeaAmtChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4TeaAmtChart(tenantCode);
    }

    /**
     *
     * @return
     */
    public List<Map<String, String>> select4ToppingAmtChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4ToppingAmtChart(tenantCode);
    }

    /**
     *
     * @return
     */
    public List<Map<String, String>> select4SpecItemAmtChart(@Param("tenantCode") String tenantCode) {
        return summaryChartMapper.select4SpecItemAmtChart(tenantCode);
    }
}
