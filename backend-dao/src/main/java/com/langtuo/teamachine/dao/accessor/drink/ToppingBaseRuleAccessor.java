package com.langtuo.teamachine.dao.accessor.drink;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.ToppingBaseRuleMapper;
import com.langtuo.teamachine.dao.po.drink.ToppingBaseRulePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ToppingBaseRuleAccessor {
    @Resource
    private ToppingBaseRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<ToppingBaseRulePO> listByTeaCode(String tenantCode, String teaCode) {
        List<ToppingBaseRulePO> cached = getCacheList(tenantCode, teaCode);
        if (!CollectionUtils.isEmpty(cached)) {
            return cached;
        }

        List<ToppingBaseRulePO> list = mapper.selectList(tenantCode, teaCode);

        setCacheList(tenantCode, teaCode, list);
        return list;
    }

    public int insertBatch(List<ToppingBaseRulePO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (ToppingBaseRulePO po : poList) {
                deleteCacheList(po.getTenantCode(), po.getTeaCode());
            }
        }
        return inserted;
    }

    public int deleteByTeaCode(String tenantCode, String teaCode) {
        int deleted = mapper.delete(tenantCode, teaCode);
        if (deleted > CommonConsts.DB_DELETED_ZERO_ROW) {
            deleteCacheList(tenantCode, teaCode);
        }
        return deleted;
    }

    public int countByToppingCode(String tenantCode, String toppingCode) {
        int count = mapper.countByToppingCode(tenantCode, toppingCode);
        return count;
    }

    private String getCacheListKey(String tenantCode, String teaCode) {
        return "toppingBaseRuleAcc-" + tenantCode + "-" + teaCode;
    }

    private String getCacheCountKey(String tenantCode, String toppingCode) {
        return "toppingBaseRuleAcc-cnt-" + tenantCode + "-" + toppingCode;
    }

    private Integer getCacheCount(String tenantCode, String toppingCode) {
        String key = getCacheCountKey(tenantCode, toppingCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String tenantCode, String toppingCode, Integer count) {
        String key = getCacheCountKey(tenantCode, toppingCode);
        redisManager4Accessor.setValue(key, count);
    }

    private List<ToppingBaseRulePO> getCacheList(String tenantCode, String teaCode) {
        String key = getCacheListKey(tenantCode, teaCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ToppingBaseRulePO> poList = (List<ToppingBaseRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String teaCode, List<ToppingBaseRulePO> poList) {
        String key = getCacheListKey(tenantCode, teaCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String teaCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, teaCode));
    }
}
