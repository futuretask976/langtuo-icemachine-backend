package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.record.ActRecordMapper;
import com.langtuo.teamachine.dao.po.record.ActRecordPO;
import com.langtuo.teamachine.dao.query.record.DrainActRecordQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DrainActRecordAccessor {
    @Resource
    private ActRecordMapper mapper;

    public ActRecordPO getByIdempotentMark(String tenantCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, idempotentMark);
    }

    public PageInfo<ActRecordPO> searchByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList,
                                                           int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        DrainActRecordQuery query = new DrainActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopGroupCode(shopGroupCodeList);
        List<ActRecordPO> list = mapper.search(query);

        PageInfo<ActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<ActRecordPO> searchByShopCodeList(String tenantCode, List<String> shopCodeList,
                                                      int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        DrainActRecordQuery query = new DrainActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllShopCode(shopCodeList);
        List<ActRecordPO> list = mapper.search(query);

        PageInfo<ActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(ActRecordPO po) {
        return mapper.insert(po);
    }

    public int delete(String tenantCode, String idempotentMark) {
        return mapper.delete(tenantCode, idempotentMark);
    }
}
