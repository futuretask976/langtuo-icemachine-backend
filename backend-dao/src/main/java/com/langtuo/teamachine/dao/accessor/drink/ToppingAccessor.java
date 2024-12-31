package com.langtuo.teamachine.dao.accessor.drink;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.ToppingMapper;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.dao.query.drink.ToppingQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ToppingAccessor {
    @Resource
    private ToppingMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public ToppingPO getByToppingCode(String tenantCode, String toppingCode) {
        // 首先访问缓存
        ToppingPO cached = getCache(tenantCode, toppingCode);
        if (cached != null) {
            return cached;
        }

        ToppingPO po = mapper.selectOne(tenantCode, toppingCode);

        // 设置缓存
        setCache(tenantCode, toppingCode, po);
        return po;
    }

    public List<ToppingPO> list(String tenantCode) {
        // 首先访问缓存
        List<ToppingPO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }
        
        List<ToppingPO> list = mapper.selectList(tenantCode);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public PageInfo<ToppingPO> search(String tenantCode, String toppingCode, String toppingName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ToppingQuery query = new ToppingQuery();
        query.setTenantCode(tenantCode);
        query.setToppingName(StringUtils.isBlank(toppingName) ? null : toppingName);
        query.setToppingCode(StringUtils.isBlank(toppingCode) ? null : toppingCode);
        List<ToppingPO> list = mapper.search(query);

        PageInfo<ToppingPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(ToppingPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(po.getTenantCode(), po.getToppingTypeCode());
        }
        return inserted;
    }

    public int update(ToppingPO po) {
        ToppingPO exist = mapper.selectOne(po.getTenantCode(), po.getToppingCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getToppingCode());
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(exist.getTenantCode(), exist.getToppingTypeCode());
            deleteCacheCount(po.getTenantCode(), po.getToppingTypeCode());
        }
        return updated;
    }

    public int deleteByToppingCode(String tenantCode, String toppingCode) {
        ToppingPO po = getByToppingCode(tenantCode, toppingCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, toppingCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getToppingCode());
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(po.getTenantCode(), po.getToppingTypeCode());
        }
        return deleted;
    }

    public int countByToppingTypeCode(String tenantCode, String toppingTypeCode) {
        int count = mapper.countByToppingTypeCode(tenantCode, toppingTypeCode);
        return count;
    }

    private String getCacheKey(String tenantCode, String toppingCode) {
        return "toppingAcc-" + tenantCode + "-" + toppingCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "toppingAcc-" + tenantCode;
    }

    private String getCacheCountKey(String tenantCode, String toppingTypeCode) {
        return "toppingAcc-cnt-" + tenantCode + "-" + toppingTypeCode;
    }

    private Integer getCacheCount(String tenantCode, String toppingTypeCode) {
        String key = getCacheCountKey(tenantCode, toppingTypeCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String tenantCode, String toppingTypeCode, Integer count) {
        String key = getCacheCountKey(tenantCode, toppingTypeCode);
        redisManager4Accessor.setValue(key, count);
    }

    private ToppingPO getCache(String tenantCode, String toppingCode) {
        String key = getCacheKey(tenantCode, toppingCode);
        Object cached = redisManager4Accessor.getValue(key);
        ToppingPO po = (ToppingPO) cached;
        return po;
    }

    private List<ToppingPO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ToppingPO> poList = (List<ToppingPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<ToppingPO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String toppingCode, ToppingPO po) {
        String key = getCacheKey(tenantCode, toppingCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String toppingCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, toppingCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }

    private void deleteCacheCount(String tenantCode, String toppingTypeCode) {
        redisManager4Accessor.deleteKey(getCacheCountKey(tenantCode, toppingTypeCode));
    }
}
