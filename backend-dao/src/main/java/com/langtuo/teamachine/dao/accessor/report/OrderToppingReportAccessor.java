package com.langtuo.teamachine.dao.accessor.report;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.report.OrderToppingReportMapper;
import com.langtuo.teamachine.dao.po.report.OrderToppingReportPO;
import com.langtuo.teamachine.dao.query.report.OrderToppingReportByShopQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderToppingReportAccessor {
    @Resource
    private OrderToppingReportMapper mapper;

    public List<OrderToppingReportPO> calcByOrderCreatedDay(String tenantCode, String orderCreatedDay) {
        return mapper.calcByDay(tenantCode, orderCreatedDay);
    }

    public PageInfo<OrderToppingReportPO> searchByShopGroupCode(String tenantCode,
            List<String> orderCreatedDayList, List<String> shopGroupCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderToppingReportByShopQuery query = new OrderToppingReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<OrderToppingReportPO> list = mapper.search(query);

        PageInfo<OrderToppingReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<OrderToppingReportPO> searchByShopCode(String tenantCode, List<String> orderCreatedDayList,
            List<String> shopCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderToppingReportByShopQuery query = new OrderToppingReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopCode(shopCodeList);
        List<OrderToppingReportPO> list = mapper.search(query);

        PageInfo<OrderToppingReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(OrderToppingReportPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String orderCreatedDay) {
        return mapper.delete(tenantCode, orderCreatedDay);
    }
}
