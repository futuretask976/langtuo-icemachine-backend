package com.langtuo.teamachine.dao.accessor.rule;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.rule.WarningRuleMapper;
import com.langtuo.teamachine.dao.query.rule.WarningRuleQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WarningRuleAccessor {
    @Resource
    private WarningRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public WarningRulePO getByWarningRuleCode(String tenantCode, String warningRuleCode) {
        WarningRulePO cached = setCache(tenantCode, warningRuleCode);
        if (cached != null) {
            return cached;
        }

        WarningRulePO po = mapper.selectOne(tenantCode, warningRuleCode);
        setCache(tenantCode, warningRuleCode, po);
        return po;
    }

    public List<WarningRulePO> list(String tenantCode) {
        // 首先访问缓存
        List<WarningRulePO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<WarningRulePO> list = mapper.selectList(tenantCode, null);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public List<WarningRulePO> listByWarningRuleCode(String tenantCode, List<String> warningruleCodeList) {
        // 这里只是在每台机器初始化的时候会调用，所以先不加缓存
        List<WarningRulePO> list = mapper.selectList(tenantCode, warningruleCodeList);
        return list;
    }

    public PageInfo<WarningRulePO> search(String tenantCode, String warningRuleCode, String warningRuleName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        WarningRuleQuery query = new WarningRuleQuery();
        query.setTenantCode(tenantCode);
        query.setWarningRuleCode(StringUtils.isBlank(warningRuleCode) ? null : warningRuleCode);
        query.setWarningRuleName(StringUtils.isBlank(warningRuleName) ? null : warningRuleName);
        List<WarningRulePO> list = mapper.search(query);

        PageInfo<WarningRulePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int update(WarningRulePO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getWarningRuleCode());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int insert(WarningRulePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getWarningRuleCode());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int deleteByWarningRuleCode(String tenantCode, String warningRuleCode) {
        WarningRulePO po = getByWarningRuleCode(tenantCode, warningRuleCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, warningRuleCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getWarningRuleCode());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String warningRuleCode) {
        return "warningRuleAcc-" + tenantCode + "-" + warningRuleCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "warningRuleAcc-" + tenantCode;
    }

    private WarningRulePO setCache(String tenantCode, String warningRuleCode) {
        String key = getCacheKey(tenantCode, warningRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        WarningRulePO po = (WarningRulePO) cached;
        return po;
    }

    private List<WarningRulePO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<WarningRulePO> poList = (List<WarningRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<WarningRulePO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String warningRuleCode, WarningRulePO po) {
        String key = getCacheKey(tenantCode, warningRuleCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String warningRuleCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, warningRuleCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
