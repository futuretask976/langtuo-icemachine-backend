package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.query.record.ActRecordQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CleanActRecordAccessor {
    @Resource
    private CleanActRecordMapper mapper;

    public CleanActRecordPO getByIdempotentMark(String tenantCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, idempotentMark);
    }

    public PageInfo<CleanActRecordPO> searchByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ActRecordQuery query = new ActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllMachineGroupCode(shopGroupCodeList);
        List<CleanActRecordPO> list = mapper.search(query);

        PageInfo<CleanActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<CleanActRecordPO> searchByShopCodeList(String tenantCode, List<String> shopCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ActRecordQuery query = new ActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopCode(shopCodeList);
        List<CleanActRecordPO> list = mapper.search(query);

        PageInfo<CleanActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(CleanActRecordPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String idempotentMark) {
        return mapper.delete(tenantCode, idempotentMark);
    }
}
