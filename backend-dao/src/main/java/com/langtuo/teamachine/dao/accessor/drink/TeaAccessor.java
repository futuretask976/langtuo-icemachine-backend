package com.langtuo.teamachine.dao.accessor.drink;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.TeaMapper;
import com.langtuo.teamachine.dao.po.drink.TeaPO;
import com.langtuo.teamachine.dao.query.drink.TeaQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TeaAccessor {
    @Resource
    private TeaMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public TeaPO getByTeaCode(String tenantCode, String teaCode) {
        // 首先访问缓存
        TeaPO cached = getCache(tenantCode, teaCode);
        if (cached != null) {
            return cached;
        }

        TeaPO po = mapper.selectOne(tenantCode, teaCode);

        // 设置缓存
        setCachedTea(tenantCode, teaCode, po);
        return po;
    }

    public List<TeaPO> list(String tenantCode) {
        // 首先访问缓存
        List<TeaPO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<TeaPO> list = mapper.selectList(tenantCode);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public PageInfo<TeaPO> search(String tenantCode, String teaCode, String teaName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TeaQuery query = new TeaQuery();
        query.setTenantCode(tenantCode);
        query.setTeaName(StringUtils.isBlank(teaName) ? null : teaName);
        query.setTeaCode(StringUtils.isBlank(teaCode) ? null : teaCode);
        List<TeaPO> list = mapper.search(query);

        PageInfo<TeaPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(TeaPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(po.getTenantCode(), po.getTeaTypeCode());
        }
        return inserted;
    }

    public int update(TeaPO po) {
        TeaPO exist = mapper.selectOne(po.getTenantCode(), po.getTeaCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getTeaCode());
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(exist.getTenantCode(), exist.getTeaTypeCode());
            deleteCacheCount(po.getTenantCode(), po.getTeaTypeCode());
        }
        return updated;
    }

    public int deleteByTeaCode(String tenantCode, String teaCode) {
        TeaPO po = getByTeaCode(tenantCode, teaCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, teaCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getTeaCode());
            deleteCacheList(po.getTenantCode());
            deleteCacheCount(po.getTenantCode(), po.getTeaTypeCode());
        }
        return deleted;
    }

    public int countByTeaTypeCode(String tenantCode, String teaTypeCode) {
        int count = mapper.countByTeaTypeCode(tenantCode, teaTypeCode);
        return count;
    }

    private String getCacheCountKey(String tenantCode, String shopGroupCode) {
        return "teaAcc-cnt-" + tenantCode + "-" + shopGroupCode;
    }

    private Integer getCacheCount(String tenantCode, String teaTypeCode) {
        String key = getCacheCountKey(tenantCode, teaTypeCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String tenantCode, String teaTypeCode, Integer count) {
        String key = getCacheCountKey(tenantCode, teaTypeCode);
        redisManager4Accessor.setValue(key, count);
    }

    private String getCacheKey(String tenantCode, String teaCode) {
        return "teaAcc-" + tenantCode + "-" + teaCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "teaAcc-" + tenantCode;
    }

    private TeaPO getCache(String tenantCode, String teaCode) {
        String key = getCacheKey(tenantCode, teaCode);
        Object cached = redisManager4Accessor.getValue(key);
        TeaPO po = (TeaPO) cached;
        return po;
    }

    private List<TeaPO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<TeaPO> poList = (List<TeaPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<TeaPO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCachedTea(String tenantCode, String teaCode, TeaPO po) {
        String key = getCacheKey(tenantCode, teaCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String teaCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, teaCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }

    private void deleteCacheCount(String tenantCode, String teaTypeCode) {
        redisManager4Accessor.deleteKey(getCacheCountKey(tenantCode, teaTypeCode));
    }
}
