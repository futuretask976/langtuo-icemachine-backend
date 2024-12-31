package com.langtuo.teamachine.biz.service.report;

import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.report.SummaryChartService;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.dao.accessor.SummaryChartAccessor;
import com.langtuo.teamachine.dao.accessor.drink.SpecItemAccessor;
import com.langtuo.teamachine.dao.accessor.drink.TeaAccessor;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.drink.SpecItemPO;
import com.langtuo.teamachine.dao.po.drink.TeaPO;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SummayChartServiceImpl implements SummaryChartService {
    @Resource
    private SummaryChartAccessor summaryChartAccessor;

    @Resource
    private TeaAccessor teaAccessor;

    @Resource
    private ShopAccessor shopAccessor;

    @Resource
    private ToppingAccessor toppingAccessor;

    @Resource
    private SpecItemAccessor specItemAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4TeaAmtByDayChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4TeaAmtByDayChart(tenantCode);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String teaCode = map.get("teaCode");
                TeaPO teaPO = teaAccessor.getByTeaCode(tenantCode, teaCode);
                map.put("teaName", teaPO.getTeaName());
            });
        }

        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4OrgShopChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4OrgShopChart(tenantCode);
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4DeployChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4DeployChart(tenantCode);
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4ShopMachineChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4ShopMachineChart(tenantCode);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String shopCode = map.get("shopCode");
                ShopPO shopPO = shopAccessor.getByShopCode(tenantCode, shopCode);
                map.put("shopName", shopPO.getShopName());
            });
        }
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4OrderAmtChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4OrderAmtChart(tenantCode);
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4TeaAmtChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4TeaAmtChart(tenantCode);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String teaCode = map.get("teaCode");
                TeaPO teaPO = teaAccessor.getByTeaCode(tenantCode, teaCode);
                map.put("teaName", teaPO.getTeaName());
            });
        }
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4ToppingAmtChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4ToppingAmtChart(tenantCode);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String toppingCode = map.get("toppingCode");
                if (StringUtils.isBlank(toppingCode)) {
                    map.put("toppingName", "未知");
                } else {
                    ToppingPO toppingPO = toppingAccessor.getByToppingCode(tenantCode, toppingCode);
                    map.put("toppingName", toppingPO.getToppingName());
                }
            });
        }
        return TeaMachineResult.success(result);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<Map<String, String>>> select4SpecItemAmtChart(String tenantCode) {
        List<Map<String, String>> result = summaryChartAccessor.select4SpecItemAmtChart(tenantCode);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String specItemCode = map.get("specItemCode");
                if (StringUtils.isBlank(specItemCode)) {
                    map.put("specItemName", "未知");
                } else {
                    SpecItemPO specItemPO = specItemAccessor.getBySpecItemCode(tenantCode, specItemCode);
                    map.put("specItemName", specItemPO.getSpecItemName());
                }
            });
        }
        return TeaMachineResult.success(result);
    }
}
