package com.langtuo.teamachine.dao.accessor.record;

import com.langtuo.teamachine.dao.mapper.record.OrderToppingActRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderToppingActRecordAccessor {
    @Resource
    private OrderToppingActRecordMapper mapper;

    public List<OrderToppingActRecordPO> listByIdempotentMark(String tenantCode, String shopGroupCode,
            String idempotentMark) {
        List<OrderToppingActRecordPO> list = mapper.selectList(tenantCode, shopGroupCode, idempotentMark);
        return list;
    }

    public int insert(OrderToppingActRecordPO po) {
        return mapper.insert(po);
    }

    public int insertBatch(List<OrderToppingActRecordPO> poList) {
        return mapper.insertBatch(poList);
    }

    public int delete(String tenantCode, String shopGroupCode, String idempotentMark) {
        return mapper.delete(tenantCode, shopGroupCode, idempotentMark);
    }
}
