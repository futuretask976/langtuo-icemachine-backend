package com.langtuo.teamachine.dao.accessor.record;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.record.ActRecordMapper;
import com.langtuo.teamachine.dao.po.record.ActRecordPO;
import com.langtuo.teamachine.dao.query.record.ActRecordQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ActRecordAccessor {
    @Resource
    private ActRecordMapper mapper;

    public ActRecordPO getByIdempotentMark(String tenantCode, String idempotentMark) {
        return mapper.selectOne(tenantCode, idempotentMark);
    }

    public PageInfo<ActRecordPO> searchByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ActRecordQuery query = new ActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllMachineGroupCode(shopGroupCodeList);
        List<ActRecordPO> list = mapper.search(query);

        PageInfo<ActRecordPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<ActRecordPO> searchByShopCodeList(String tenantCode, List<String> machineGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ActRecordQuery query = new ActRecordQuery();
        query.setTenantCode(tenantCode);
        query.addAllMachineGroupCode(machineGroupCodeList);
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
