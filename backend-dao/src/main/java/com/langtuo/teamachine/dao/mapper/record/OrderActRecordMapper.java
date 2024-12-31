package com.langtuo.teamachine.dao.mapper.record;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.query.record.OrderActRecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderActRecordMapper {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_act_record")
    OrderActRecordPO selectOne(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode,
            @Param("idempotentMark") String idempotentMark);

    /**
     *
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_act_record")
    List<OrderActRecordPO> search(OrderActRecordQuery query);

    /**
     *
     * @param po
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_act_record")
    int insert(OrderActRecordPO po);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_act_record")
    int delete(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode,
            @Param("idempotentMark") String idempotentMark);
}
