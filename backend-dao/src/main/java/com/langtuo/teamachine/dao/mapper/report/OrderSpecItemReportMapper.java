package com.langtuo.teamachine.dao.mapper.report;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.report.OrderSpecItemReportPO;
import com.langtuo.teamachine.dao.query.report.OrderSpecItemReportByShopQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderSpecItemReportMapper {
    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    List<OrderSpecItemReportPO> calcByDay(@Param("tenantCode") String tenantCode,
            @Param("orderCreatedDay") String orderCreatedDay);

    /**
     *
     * @return
     */
    List<OrderSpecItemReportPO> search(OrderSpecItemReportByShopQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(OrderSpecItemReportPO po);

    /**
     *
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("orderCreatedDay") String orderCreatedDay);
}
