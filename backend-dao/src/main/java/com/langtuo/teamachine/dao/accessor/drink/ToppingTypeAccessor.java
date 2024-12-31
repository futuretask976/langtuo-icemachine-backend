package com.langtuo.teamachine.dao.accessor.drink;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.ToppingTypeMapper;
import com.langtuo.teamachine.dao.po.drink.ToppingTypePO;
import com.langtuo.teamachine.dao.query.drink.ToppingTypeQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ToppingTypeAccessor {
    @Resource
    private ToppingTypeMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public ToppingTypePO getByToppingTypeCode(String tenantCode, String toppingTypeCode) {
        // 首先访问缓存
        ToppingTypePO cached = getCache(tenantCode, toppingTypeCode);
        if (cached != null) {
            return cached;
        }

        ToppingTypePO po = mapper.selectOne(tenantCode, toppingTypeCode);

        // 设置缓存
        setCache(tenantCode, toppingTypeCode, po);
        return po;
    }

    public List<ToppingTypePO> selectList(String tenantCode) {
        // 首先访问缓存
        List<ToppingTypePO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<ToppingTypePO> list = mapper.selectList(tenantCode);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public PageInfo<ToppingTypePO> search(String tenantCode, String toppingTypeCode, String toppingTypeName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ToppingTypeQuery query = new ToppingTypeQuery();
        query.setTenantCode(tenantCode);
        query.setToppingTypeName(StringUtils.isBlank(toppingTypeName) ? null : toppingTypeName);
        query.setToppingTypeCode(StringUtils.isBlank(toppingTypeCode) ? null : toppingTypeCode);
        List<ToppingTypePO> list = mapper.search(query);

        PageInfo<ToppingTypePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(ToppingTypePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getToppingTypeCode(), po.getToppingTypeName());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int update(ToppingTypePO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getToppingTypeCode(), po.getToppingTypeName());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int delete(String tenantCode, String toppingTypeCode) {
        ToppingTypePO po = getByToppingTypeCode(tenantCode, toppingTypeCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, toppingTypeCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getToppingTypeCode(), po.getToppingTypeName());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String toppingTypeCode) {
        return "toppingTypeAcc-" + tenantCode + "-" + toppingTypeCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "toppingTypeAcc-" + tenantCode;
    }

    private ToppingTypePO getCache(String tenantCode, String toppingTypeCode) {
        String key = getCacheKey(tenantCode, toppingTypeCode);
        Object cached = redisManager4Accessor.getValue(key);
        ToppingTypePO po = (ToppingTypePO) cached;
        return po;
    }

    private List<ToppingTypePO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ToppingTypePO> poList = (List<ToppingTypePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<ToppingTypePO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String toppingTypeCode, ToppingTypePO po) {
        String key = getCacheKey(tenantCode, toppingTypeCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String toppingTypeCode, String toppingTypeName) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, toppingTypeCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
