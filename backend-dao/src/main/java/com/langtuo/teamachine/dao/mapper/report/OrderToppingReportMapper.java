package com.langtuo.teamachine.dao.mapper.report;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.report.OrderToppingReportPO;
import com.langtuo.teamachine.dao.query.report.OrderToppingReportByShopQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderToppingReportMapper {
    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    List<OrderToppingReportPO> calcByDay(@Param("tenantCode") String tenantCode,
            @Param("orderCreatedDay") String orderCreatedDay);

    /**
     *
     * @return
     */
    List<OrderToppingReportPO> search(OrderToppingReportByShopQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(OrderToppingReportPO po);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("orderCreatedDay") String orderCreatedDay);
}
