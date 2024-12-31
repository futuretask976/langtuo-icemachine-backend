package com.langtuo.teamachine.dao.mapper.record;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderSpecItemActRecordMapper {
    /**
     *
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_specitem_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_specitem_act_record")
    List<OrderSpecItemActRecordPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCode") String shopGroupCode, @Param("idempotentMark") String idempotentMark);

    /**
     *
     * @param po
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_specitem_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_specitem_act_record")
    int insert(OrderSpecItemActRecordPO po);

    /**
     *
     * @param poList
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_specitem_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_specitem_act_record")
    int insertBatch(List<OrderSpecItemActRecordPO> poList);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    @TeaMachineTableShard(tableShardOpen = true, shardName = "order_specitem_act_record_sharding_", columns = {"tenantCode", "shopGroupCode"}, originName = "order_specitem_act_record")
    int delete(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode, @Param("idempotentMark") String idempotentMark);
}
