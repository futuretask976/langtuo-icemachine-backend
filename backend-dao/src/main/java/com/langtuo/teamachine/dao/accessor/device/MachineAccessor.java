package com.langtuo.teamachine.dao.accessor.device;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.device.MachineMapper;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.query.device.MachineQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MachineAccessor {
    @Resource
    private MachineMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public MachinePO getByMachineCode(String tenantCode, String machineCode) {
        MachinePO po = mapper.selectOne(tenantCode, machineCode);
        return po;
    }

    public List<MachinePO> list(String tenantCode) {
        List<MachinePO> list = mapper.selectList(tenantCode, null);
        return list;
    }

    public List<MachinePO> listByShopCode(String tenantCode, String shopCode) {
        List<MachinePO> list = mapper.selectList(tenantCode, shopCode);
        return list;
    }

    public PageInfo<MachinePO> search(String tenantCode, String machineCode, List<String> machineGroupCodeList,
                int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        MachineQuery machineQuery = new MachineQuery();
        machineQuery.setTenantCode(tenantCode);
        machineQuery.setMachineCode(StringUtils.isBlank(machineCode) ? null : machineCode);
        machineQuery.setMachineGroupCodeList(machineGroupCodeList);
        List<MachinePO> list = mapper.search(machineQuery);

        PageInfo<MachinePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(MachinePO po) {
        int inserted = mapper.insert(po);
        return inserted;
    }

    public int update(MachinePO po) {
        MachinePO exist = mapper.selectOne(po.getTenantCode(), po.getMachineCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        return updated;
    }

    public int deleteByMachineCode(String tenantCode, String machineCode) {
        MachinePO po = getByMachineCode(tenantCode, machineCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, machineCode);
        return deleted;
    }

    public int updateOnlineStateByMachineCode(String machineCode, int onlineState) {
        MachinePO po = getByMachineCode(null, machineCode);
        if (po == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.updateOnlineStateByMachineCode(machineCode, onlineState);
        return updated;
    }
}
