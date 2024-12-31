package com.langtuo.teamachine.dao.accessor.report;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.report.OrderTeaReportMapper;
import com.langtuo.teamachine.dao.po.report.OrderTeaReportPO;
import com.langtuo.teamachine.dao.query.report.OrderTeaReportByShopQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderTeaReportAccessor {
    @Resource
    private OrderTeaReportMapper mapper;

    public List<OrderTeaReportPO> calcByOrderCreatedDay(String tenantCode, String orderCreatedDay) {
        return mapper.calcByDay(tenantCode, orderCreatedDay);
    }

    public PageInfo<OrderTeaReportPO> searchByShopGroupCode(String tenantCode, List<String> orderCreatedDayList,
            List<String> shopGroupCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderTeaReportByShopQuery query = new OrderTeaReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<OrderTeaReportPO> list = mapper.search(query);

        PageInfo<OrderTeaReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<OrderTeaReportPO> searchByShopCode(String tenantCode, List<String> orderCreatedDayList,
            List<String> shopCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderTeaReportByShopQuery query = new OrderTeaReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopCode(shopCodeList);
        List<OrderTeaReportPO> list = mapper.search(query);

        PageInfo<OrderTeaReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(OrderTeaReportPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String orderCreatedDay) {
        return mapper.delete(tenantCode, orderCreatedDay);
    }
}
