package com.langtuo.teamachine.dao.accessor.drink;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.SpecMapper;
import com.langtuo.teamachine.dao.po.drink.SpecPO;
import com.langtuo.teamachine.dao.query.drink.SpecQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SpecAccessor {
    @Resource
    private SpecMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public SpecPO getBySpecCode(String tenantCode, String specCode) {
        // 首先访问缓存
        SpecPO cached = getCache(tenantCode, specCode);
        if (cached != null) {
            return cached;
        }

        SpecPO po = mapper.selectOne(tenantCode, specCode);

        // 设置缓存
        setCache(tenantCode, specCode, po);
        return po;
    }

    public List<SpecPO> list(String tenantCode) {
        // 首先访问缓存
        List<SpecPO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }
        
        List<SpecPO> list = mapper.selectList(tenantCode);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public PageInfo<SpecPO> search(String tenantCode, String specCode, String specName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SpecQuery query = new SpecQuery();
        query.setTenantCode(tenantCode);
        query.setSpecCode(StringUtils.isBlank(specCode) ? null : specCode);
        query.setSpecName(StringUtils.isBlank(specName) ? null : specName);
        List<SpecPO> list = mapper.search(query);

        PageInfo<SpecPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(SpecPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getSpecCode());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int update(SpecPO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getSpecCode());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int deleteBySpecCode(String tenantCode, String specCode) {
        SpecPO po = getBySpecCode(tenantCode, specCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, specCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getSpecCode());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String specCode) {
        return "specAcc-" + tenantCode + "-" + specCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "specAcc-" + tenantCode;
    }

    private SpecPO getCache(String tenantCode, String specCode) {
        String key = getCacheKey(tenantCode, specCode);
        Object cached = redisManager4Accessor.getValue(key);
        SpecPO po = (SpecPO) cached;
        return po;
    }

    private List<SpecPO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<SpecPO> poList = (List<SpecPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<SpecPO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String specCode, SpecPO po) {
        String key = getCacheKey(tenantCode, specCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String specCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, specCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
