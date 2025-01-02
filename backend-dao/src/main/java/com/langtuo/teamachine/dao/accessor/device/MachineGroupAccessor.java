package com.langtuo.teamachine.dao.accessor.device;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.device.MachineGroupMapper;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.query.device.MachineGroupQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MachineGroupAccessor {
    @Resource
    private MachineGroupMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public MachineGroupPO getByMachineGroupCode(String tenantCode, String shopGroupCode) {
        MachineGroupPO po = mapper.selectOne(tenantCode, shopGroupCode);
        return po;
    }

    public List<MachineGroupPO> list(String tenantCode) {
        List<MachineGroupPO> list = mapper.selectList(tenantCode);
        return list;
    }

    public PageInfo<MachineGroupPO> search(String tenantCode, String machineGroupName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        MachineGroupQuery machineGroupQuery = new MachineGroupQuery();
        machineGroupQuery.setTenantCode(tenantCode);
        machineGroupQuery.setMachineGroupName(StringUtils.isBlank(machineGroupName) ? null : machineGroupName);
        List<MachineGroupPO> list = mapper.search(machineGroupQuery);

        PageInfo<MachineGroupPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(MachineGroupPO po) {
        int inserted = mapper.insert(po);
        return inserted;
    }

    public int update(MachineGroupPO po) {
        MachineGroupPO exist = mapper.selectOne(po.getTenantCode(), po.getMachineGroupCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        return updated;
    }

    public int deleteByShopGroupCode(String tenantCode, String shopGroupCode) {
        MachineGroupPO po = getByMachineGroupCode(tenantCode, shopGroupCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, shopGroupCode);
        return deleted;
    }
}
