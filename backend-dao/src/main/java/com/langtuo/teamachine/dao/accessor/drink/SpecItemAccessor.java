package com.langtuo.teamachine.dao.accessor.drink;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.SpecItemMapper;
import com.langtuo.teamachine.dao.po.drink.SpecItemPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SpecItemAccessor {
    @Resource
    private SpecItemMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public SpecItemPO getBySpecItemCode(String tenantCode, String specItemCode) {
        // 首先访问缓存
        SpecItemPO cached = getCache(tenantCode, specItemCode, null);
        if (cached != null) {
            return cached;
        }

        SpecItemPO po = mapper.selectOne(tenantCode, specItemCode, null);

        // 设置缓存
        setCache(tenantCode, specItemCode, null, po);
        return po;
    }

    public SpecItemPO getBySpecItemName(String tenantCode, String specItemName) {
        // 首先访问缓存
        SpecItemPO cached = getCache(tenantCode, null, specItemName);
        if (cached != null) {
            return cached;
        }

        SpecItemPO po = mapper.selectOne(tenantCode, null, specItemName);

        // 设置缓存
        setCache(tenantCode, null, specItemName, po);
        return po;
    }

    public List<SpecItemPO> listBySpecCode(String tenantCode, String specCode) {
        // 首先访问缓存
        List<SpecItemPO> cachedList = getCacheList(tenantCode, specCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<SpecItemPO> list = mapper.selectList(tenantCode, specCode);

        // 设置缓存
        setCacheList(tenantCode, specCode, list);
        return list;
    }

    public List<SpecItemPO> list(String tenantCode) {
        List<SpecItemPO> list = mapper.selectList(tenantCode, null);
        return list;
    }

    public int insertBatch(List<SpecItemPO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (SpecItemPO po : poList) {
                deleteCacheOne(po.getTenantCode(), po.getSpecItemCode(), po.getSpecItemName());
                deleteCacheList(po.getTenantCode(), po.getSpecCode());
            }
        }
        return inserted;
    }

    public int update(SpecItemPO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getSpecItemCode(), po.getSpecItemName());
            deleteCacheList(po.getTenantCode(), po.getSpecCode());
        }
        return updated;
    }

    public int deleteBySpecCode(String tenantCode, String specCode) {
        List<SpecItemPO> specItemPOList = mapper.selectList(tenantCode, specCode);

        int deleted = mapper.delete(tenantCode, specCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            for (SpecItemPO specItemPO : specItemPOList) {
                deleteCacheOne(tenantCode, specItemPO.getSpecItemCode(), specItemPO.getSpecItemName());
            }
            deleteCacheList(tenantCode, specCode);
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String specCode) {
        return "specItemAcc-" + tenantCode + "-" + specCode;
    }

    private String getCacheKey(String tenantCode, String specItemCode, String specItemName) {
        return "specItemAcc-" + tenantCode + "-" + specItemCode + "-" + specItemName;
    }

    private SpecItemPO getCache(String tenantCode, String specItemCode, String specItemName) {
        String key = getCacheKey(tenantCode, specItemCode, specItemName);
        Object cached = redisManager4Accessor.getValue(key);
        SpecItemPO po = (SpecItemPO) cached;
        return po;
    }

    private List<SpecItemPO> getCacheList(String tenantCode, String specCode) {
        String key = getCacheListKey(tenantCode, specCode);
        Object cached = redisManager4Accessor.getValue(key);

        List<SpecItemPO> poList = (List<SpecItemPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String specCode, List<SpecItemPO> poList) {
        String key = getCacheListKey(tenantCode, specCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String specItemCode, String specItemName, SpecItemPO po) {
        String key = getCacheKey(tenantCode, specItemCode, specItemName);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String specItemCode, String specItemName) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, specItemCode, null));
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, null, specItemName));
    }

    private void deleteCacheList(String tenantCode, String specCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, specCode));
    }
}
