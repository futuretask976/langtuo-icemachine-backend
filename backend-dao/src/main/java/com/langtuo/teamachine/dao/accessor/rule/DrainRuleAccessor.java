package com.langtuo.teamachine.dao.accessor.rule;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DrainRuleAccessor {
    @Resource
    private DrainRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public DrainRulePO getByDrainRuleCode(String tenantCode, String drainRuleCode) {
        DrainRulePO cached = setCache(tenantCode, drainRuleCode);
        if (cached != null) {
            return cached;
        }

        DrainRulePO po = mapper.selectOne(tenantCode, drainRuleCode);
        setCache(tenantCode, drainRuleCode, po);
        return po;
    }

    public List<DrainRulePO> list(String tenantCode) {
        // 首先访问缓存
        List<DrainRulePO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<DrainRulePO> list = mapper.selectList(tenantCode, null);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public List<DrainRulePO> listByDrainRuleCode(String tenantCode, List<String> drainRuleCodeList) {
        // 这里只是在每台机器初始化的时候会调用，所以先不加缓存
        List<DrainRulePO> list = mapper.selectList(tenantCode, drainRuleCodeList);
        return list;
    }

    public PageInfo<DrainRulePO> search(String tenantCode, String drainRuleCode, String drainRuleName,
                                        int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        DrainRuleQuery query = new DrainRuleQuery();
        query.setTenantCode(tenantCode);
        query.setDrainRuleCode(StringUtils.isBlank(drainRuleCode) ? null : drainRuleCode);
        query.setDrainRuleName(StringUtils.isBlank(drainRuleName) ? null : drainRuleName);
        List<DrainRulePO> list = mapper.search(query);

        PageInfo<DrainRulePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int update(DrainRulePO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getDrainRuleCode());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int insert(DrainRulePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getDrainRuleCode());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int deleteByDrainRuleCode(String tenantCode, String drainRuleCode) {
        DrainRulePO po = getByDrainRuleCode(tenantCode, drainRuleCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, drainRuleCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getDrainRuleCode());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String drainRuleCode) {
        return "drainRuleAcc-" + tenantCode + "-" + drainRuleCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "drainRuleAcc-" + tenantCode;
    }

    private DrainRulePO setCache(String tenantCode, String drainRuleCode) {
        String key = getCacheKey(tenantCode, drainRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        DrainRulePO po = (DrainRulePO) cached;
        return po;
    }

    private List<DrainRulePO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<DrainRulePO> poList = (List<DrainRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<DrainRulePO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String drainRuleCode, DrainRulePO po) {
        String key = getCacheKey(tenantCode, drainRuleCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String drainRuleCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, drainRuleCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
