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
        // 首先访问缓存
        MachinePO cached = getCache(tenantCode, machineCode);
        if (cached != null) {
            return cached;
        }

        MachinePO po = mapper.selectOne(tenantCode, machineCode);

        // 设置缓存
        setCache(tenantCode, machineCode, po);
        return po;
    }

    public List<MachinePO> list(String tenantCode) {
        // 首先访问缓存
        List<MachinePO> cachedList = getCacheList(tenantCode, null);
        if (cachedList != null) {
            return cachedList;
        }

        List<MachinePO> list = mapper.selectList(tenantCode, null);

        // 设置缓存
        setCacheList(tenantCode, null, list);
        return list;
    }

    public List<MachinePO> listByShopCode(String tenantCode, String shopCode) {
        // 首先访问缓存
        List<MachinePO> cachedList = getCacheList(tenantCode, shopCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<MachinePO> list = mapper.selectList(tenantCode, shopCode);

        // 设置缓存
        setCacheList(tenantCode, shopCode, list);
        return list;
    }

    public int countByModelCode(String modelCode) {
        int count = mapper.countByModelCode(modelCode);
        return count;
    }

    public PageInfo<MachinePO> search(String tenantCode, String machineCode, String screenCode, String elecBoardCode,
            List<String> shopCodeList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        MachineQuery machineQuery = new MachineQuery();
        machineQuery.setTenantCode(tenantCode);
        machineQuery.setMachineCode(StringUtils.isBlank(machineCode) ? null : machineCode);
        machineQuery.setScreenCode(StringUtils.isBlank(screenCode) ? null : screenCode);
        machineQuery.setElecBoardCode(StringUtils.isBlank(elecBoardCode) ? null : elecBoardCode);
        machineQuery.setShopCodeList(shopCodeList);
        List<MachinePO> list = mapper.search(machineQuery);

        PageInfo<MachinePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(MachinePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheList(po.getTenantCode(), po.getShopCode());
            deleteCacheCountByModelCode(po.getModelCode());
        }
        return inserted;
    }

    public int update(MachinePO po) {
        MachinePO exist = mapper.selectOne(po.getTenantCode(), po.getMachineCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getMachineCode());
            deleteCacheList(po.getTenantCode(), po.getShopCode());
            deleteCacheCountByModelCode(exist.getModelCode());
            deleteCacheCountByModelCode(po.getModelCode());
        }
        return updated;
    }

    public int deleteByMachineCode(String tenantCode, String machineCode) {
        MachinePO po = getByMachineCode(tenantCode, machineCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, machineCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, machineCode);
            deleteCacheList(tenantCode, po.getShopCode());
            deleteCacheCountByModelCode(po.getModelCode());
        }
        return deleted;
    }

    public int updateOnlineStateByMachineCode(String machineCode, int onlineState) {
        MachinePO po = getByMachineCode(null, machineCode);
        if (po == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.updateOnlineStateByMachineCode(machineCode, onlineState);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), machineCode);
            deleteCacheList(po.getTenantCode(), po.getShopCode());
            deleteCacheCountByModelCode(po.getModelCode());
        }
        return updated;
    }

    private String getCacheKey(String tenantCode, String machineCode) {
        return "machineAcc-" + tenantCode + "-" + machineCode;
    }

    private String getCacheListKey(String tenantCode, String shopCode) {
        return "machineAcc-" + tenantCode + "-" + shopCode;
    }

    private String getCacheCountKeyByModelCode(String modelCode) {
        return "machineAcc-cnt-" + modelCode;
    }

    private MachinePO getCache(String tenantCode, String machineCode) {
        String key = getCacheKey(tenantCode, machineCode);
        Object cached = redisManager4Accessor.getValue(key);
        MachinePO po = (MachinePO) cached;
        return po;
    }

    private List<MachinePO> getCacheList(String tenantCode, String shopCode) {
        String key = getCacheListKey(tenantCode, shopCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<MachinePO> poList = (List<MachinePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String shopCode, List<MachinePO> poList) {
        String key = getCacheListKey(tenantCode, shopCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String machineCode, MachinePO po) {
        String key = getCacheKey(tenantCode, machineCode);
        redisManager4Accessor.setValue(key, po);
    }

    private Integer getCacheCountByModelCode(String modelCode) {
        String key = getCacheCountKeyByModelCode(modelCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String modelCode, Integer count) {
        String key = getCacheCountKeyByModelCode(modelCode);
        redisManager4Accessor.setValue(key, count);
    }

    private void deleteCacheOne(String tenantCode, String machineCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, machineCode));
    }

    private void deleteCacheList(String tenantCode, String shopCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, shopCode));
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, null));
    }

    private void deleteCacheCountByModelCode(String modelCode) {
        redisManager4Accessor.deleteKey(getCacheCountKeyByModelCode(modelCode));
    }
}
