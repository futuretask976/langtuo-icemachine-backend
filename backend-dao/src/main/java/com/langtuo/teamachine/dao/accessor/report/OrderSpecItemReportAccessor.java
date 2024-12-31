package com.langtuo.teamachine.dao.accessor.report;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.report.OrderSpecItemReportMapper;
import com.langtuo.teamachine.dao.po.report.OrderSpecItemReportPO;
import com.langtuo.teamachine.dao.query.report.OrderSpecItemReportByShopQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderSpecItemReportAccessor {
    @Resource
    private OrderSpecItemReportMapper mapper;

    public List<OrderSpecItemReportPO> calcByOrderCreatedDay(String tenantCode, String orderCreatedDay) {
        return mapper.calcByDay(tenantCode, orderCreatedDay);
    }

    public PageInfo<OrderSpecItemReportPO> searchByShopGroupCode(String tenantCode, List<String> orderCreatedDayList,
            List<String> shopGroupCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderSpecItemReportByShopQuery query = new OrderSpecItemReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<OrderSpecItemReportPO> list = mapper.search(query);

        PageInfo<OrderSpecItemReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<OrderSpecItemReportPO> searchByShopCode(String tenantCode, List<String> orderCreatedDayList,
            List<String> shopCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderSpecItemReportByShopQuery query = new OrderSpecItemReportByShopQuery();
        query.setTenantCode(tenantCode);
        query.addOrderCreatedDay(orderCreatedDayList);
        query.addAllShopCode(shopCodeList);
        List<OrderSpecItemReportPO> list = mapper.search(query);

        PageInfo<OrderSpecItemReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(OrderSpecItemReportPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String orderCreatedDay) {
        return mapper.delete(tenantCode, orderCreatedDay);
    }
}
