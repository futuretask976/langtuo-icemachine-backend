package com.langtuo.teamachine.dao.accessor.drink;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.SpecItemRuleMapper;
import com.langtuo.teamachine.dao.po.drink.SpecItemRulePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SpecItemRuleAccessor {
    @Resource
    private SpecItemRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<SpecItemRulePO> listByTeaCode(String tenantCode, String teaCode) {
        // 首先访问缓存
        List<SpecItemRulePO> cachedList = getCacheList(tenantCode, teaCode);
        if (cachedList != null) {
            return cachedList;
        }

        List<SpecItemRulePO> list = mapper.selectListByTeaCode(tenantCode, teaCode);

        setCacheList(tenantCode, teaCode, list);
        return list;
    }

    public int insertBatch(List<SpecItemRulePO> poList) {
        return mapper.insertBatch(poList);
    }

    public int deleteByTeaCode(String tenantCode, String teaCode) {
        int deleted = mapper.deleteByTeaCode(tenantCode, teaCode);
        if (deleted > CommonConsts.DB_DELETED_ZERO_ROW) {
            deleteCacheList(tenantCode, teaCode);
        }
        return deleted;
    }

    public int countBySpecCode(String tenantCode, String specCode) {
        int count = mapper.countBySpecCode(tenantCode, specCode);
        return count;
    }

    public int countBySpecItemCode(String tenantCode, List<String> specItemCodeList) {
        int count = mapper.countBySpecItemCode(tenantCode, specItemCodeList);
        return count;
    }

    private String getCacheListKey(String tenantCode, String teaCode) {
        return "teaSpecItemAcc-" + tenantCode + "-" + teaCode;
    }

    private String getCacheCountKey(String tenantCode, String specCode) {
        return "teaSpecItemAcc-cnt-" + tenantCode + "-" + specCode;
    }

    private Integer getCacheCount(String tenantCode, String specCode) {
        String key = getCacheCountKey(tenantCode, specCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String tenantCode, String specCode, Integer count) {
        String key = getCacheCountKey(tenantCode, specCode);
        redisManager4Accessor.setValue(key, count);
    }

    private List<SpecItemRulePO> getCacheList(String tenantCode, String teaCode) {
        String key = getCacheListKey(tenantCode, teaCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<SpecItemRulePO> poList = (List<SpecItemRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String teaCode, List<SpecItemRulePO> poList) {
        String key = getCacheListKey(tenantCode, teaCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String teaCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, teaCode));
    }
}
