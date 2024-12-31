package com.langtuo.teamachine.dao.accessor.report;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.report.OrderReportMapper;
import com.langtuo.teamachine.dao.po.report.OrderReportPO;
import com.langtuo.teamachine.dao.query.report.OrderReportQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderReportAccessor {
    @Resource
    private OrderReportMapper mapper;

    public List<OrderReportPO> calcByOrderCreatedDay(String tenantCode, String orderCreatedDay) {
        return mapper.calcByDay(tenantCode, orderCreatedDay);
    }

    public PageInfo<OrderReportPO> search(String tenantCode, List<String> orderCreatedDayList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderReportQuery query = new OrderReportQuery();
        query.setTenantCode(tenantCode);
        query.addAllOrderCreatedDay(orderCreatedDayList);
        List<OrderReportPO> list = mapper.search(query);

        PageInfo<OrderReportPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(OrderReportPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String orderCreatedDay) {
        return mapper.delete(tenantCode, orderCreatedDay);
    }
}
