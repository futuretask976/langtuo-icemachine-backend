package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.record.SupplyActRecordMapper;
import com.langtuo.teamachine.dao.po.record.SupplyActRecordPO;
import com.langtuo.teamachine.dao.query.record.SupplyActRecordQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SupplyActRecordAccessor {
    @Resource
    private SupplyActRecordMapper mapper;

    public SupplyActRecordPO getByIdempotentMark(String tenantCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, idempotentMark);
    }

    public PageInfo<SupplyActRecordPO> searchByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SupplyActRecordQuery query = new SupplyActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<SupplyActRecordPO> list = mapper.search(query);

        PageInfo<SupplyActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<SupplyActRecordPO> searchByShopCodeList(String tenantCode, List<String> shopCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SupplyActRecordQuery query = new SupplyActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopCode(shopCodeList);
        List<SupplyActRecordPO> list = mapper.search(query);

        PageInfo<SupplyActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(SupplyActRecordPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String idempotentMark) {
        return mapper.delete(tenantCode, idempotentMark);
    }
}
