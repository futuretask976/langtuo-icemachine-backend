package com.langtuo.teamachine.dao.mapper.report;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.report.OrderTeaReportPO;
import com.langtuo.teamachine.dao.query.report.OrderTeaReportByShopQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderTeaReportMapper {
    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    List<OrderTeaReportPO> calcByDay(@Param("tenantCode") String tenantCode,
            @Param("orderCreatedDay") String orderCreatedDay);

    /**
     *
     * @return
     */
    List<OrderTeaReportPO> search(OrderTeaReportByShopQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(OrderTeaReportPO po);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("orderCreatedDay") String orderCreatedDay);
}
