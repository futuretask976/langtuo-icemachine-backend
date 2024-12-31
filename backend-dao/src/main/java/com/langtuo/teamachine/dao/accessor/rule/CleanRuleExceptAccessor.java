package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CleanRuleExceptAccessor {
    @Resource
    private CleanRuleExceptMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<CleanRuleExceptPO> listByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        List<CleanRuleExceptPO> cached = getCacheList(tenantCode, cleanRuleCode);
        if (cached != null) {
            return cached;
        }

        List<CleanRuleExceptPO> list = mapper.selectList(tenantCode, cleanRuleCode);

        setCacheList(tenantCode, cleanRuleCode, list);
        return list;
    }

    public int insertBatch(List<CleanRuleExceptPO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (CleanRuleExceptPO po : poList) {
                deleteCacheList(po.getTenantCode(), po.getCleanRuleCode());
            }
        }
        return inserted;
    }

    public int deleteByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        int deleted = mapper.delete(tenantCode, cleanRuleCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheList(tenantCode, cleanRuleCode);
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String cleanRuleCode) {
        return "cleanRuleExceptAcc-" + tenantCode + "-" + cleanRuleCode;
    }

    private List<CleanRuleExceptPO> getCacheList(String tenantCode, String cleanRuleCode) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<CleanRuleExceptPO> poList = (List<CleanRuleExceptPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String cleanRuleCode, List<CleanRuleExceptPO> poList) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String cleanRuleCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, cleanRuleCode));
    }
}
