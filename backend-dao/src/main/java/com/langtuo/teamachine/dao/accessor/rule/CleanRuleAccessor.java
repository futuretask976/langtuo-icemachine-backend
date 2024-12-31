package com.langtuo.teamachine.dao.accessor.rule;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.rule.CleanRuleMapper;
import com.langtuo.teamachine.dao.po.rule.ConfigRulePO;
import com.langtuo.teamachine.dao.query.rule.CleanRuleQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CleanRuleAccessor {
    @Resource
    private CleanRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public ConfigRulePO getByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        ConfigRulePO cached = setCache(tenantCode, cleanRuleCode);
        if (cached != null) {
            return cached;
        }

        ConfigRulePO po = mapper.selectOne(tenantCode, cleanRuleCode);
        setCache(tenantCode, cleanRuleCode, po);
        return po;
    }

    public List<ConfigRulePO> selectList(String tenantCode) {
        // 首先访问缓存
        List<ConfigRulePO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<ConfigRulePO> list = mapper.selectList(tenantCode, null);

        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public List<ConfigRulePO> selectListByCleanRuleCode(String tenantCode, List<String> cleanRuleCodeList) {
        // 这里只是在每台机器初始化的时候会调用，所以先不加缓存
        List<ConfigRulePO> list = mapper.selectList(tenantCode, cleanRuleCodeList);
        return list;
    }

    public PageInfo<ConfigRulePO> search(String tenantCode, String cleanRuleCode, String cleanRuleName,
                                         int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        CleanRuleQuery query = new CleanRuleQuery();
        query.setTenantCode(tenantCode);
        query.setCleanRuleCode(StringUtils.isBlank(cleanRuleCode) ? null : cleanRuleCode);
        query.setCleanRuleName(StringUtils.isBlank(cleanRuleName) ? null : cleanRuleName);
        List<ConfigRulePO> list = mapper.search(query);

        PageInfo<ConfigRulePO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int update(ConfigRulePO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getConfigRuleCode(), po.getConfigRuleName());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int insert(ConfigRulePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getConfigRuleCode(), po.getConfigRuleName());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int deleteByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        ConfigRulePO po = getByCleanRuleCode(tenantCode, cleanRuleCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, cleanRuleCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getConfigRuleCode(), po.getConfigRuleName());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String cleanRuleCode) {
        return "cleanRuleAcc-" + tenantCode + "-" + cleanRuleCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "cleanRuleAcc-" + tenantCode;
    }

    private ConfigRulePO setCache(String tenantCode, String cleanRuleCode) {
        String key = getCacheKey(tenantCode, cleanRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        ConfigRulePO po = (ConfigRulePO) cached;
        return po;
    }

    private List<ConfigRulePO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ConfigRulePO> poList = (List<ConfigRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<ConfigRulePO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String cleanRuleCode, ConfigRulePO po) {
        String key = getCacheKey(tenantCode, cleanRuleCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String cleanRuleCode, String cleanRuleName) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, cleanRuleCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
