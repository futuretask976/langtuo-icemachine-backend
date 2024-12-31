package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class InvalidActRecordAccessor {
    @Resource
    private InvalidActRecordMapper mapper;

    public InvalidActRecordPO getByIdempotentMark(String tenantCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, idempotentMark);
    }

    public PageInfo<InvalidActRecordPO> searchByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        InvalidActRecordQuery query = new InvalidActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<InvalidActRecordPO> list = mapper.search(query);

        PageInfo<InvalidActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<InvalidActRecordPO> searchByShopCodeList(String tenantCode, List<String> shopCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        InvalidActRecordQuery query = new InvalidActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopCode(shopCodeList);
        List<InvalidActRecordPO> list = mapper.search(query);

        PageInfo<InvalidActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(InvalidActRecordPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String idempotentMark) {
        return mapper.delete(tenantCode, idempotentMark);
    }
}
