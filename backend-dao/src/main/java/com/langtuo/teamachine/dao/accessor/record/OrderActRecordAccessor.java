package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderActRecordAccessor {
    @Resource
    private OrderActRecordMapper mapper;

    public OrderActRecordPO getByIdempotentMark(String tenantCode, String shopGroupCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, shopGroupCode, idempotentMark);
    }

    public PageInfo<OrderActRecordPO> searchByShopGroupCode(String tenantCode, String shopGroupCode,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderActRecordQuery query = new OrderActRecordQuery();
        query.setTenantCode(tenantCode);
        query.setShopGroupCode(shopGroupCode);
        List<OrderActRecordPO> list = mapper.search(query);

        PageInfo<OrderActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<OrderActRecordPO> searchByShopCodeList(String tenantCode, String shopGroupCode,
            List<String> shopCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        OrderActRecordQuery query = new OrderActRecordQuery();
        query.setTenantCode(tenantCode);
        query.setShopGroupCode(shopGroupCode);
        query.addAllShopCode(shopCodeList);
        List<OrderActRecordPO> list = mapper.search(query);

        PageInfo<OrderActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(OrderActRecordPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String shopGroupCode, String idempotentMark) {
        return mapper.delete(tenantCode, shopGroupCode, idempotentMark);
    }
}
