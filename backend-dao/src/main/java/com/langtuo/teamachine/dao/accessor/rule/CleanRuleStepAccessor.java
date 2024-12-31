package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CleanRuleStepAccessor {
    @Resource
    private CleanRuleStepMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<CleanRuleStepPO> listByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        List<CleanRuleStepPO> cached = getCacheList(tenantCode, cleanRuleCode);
        if (cached != null) {
            return cached;
        }

        List<CleanRuleStepPO> list = mapper.selectList(tenantCode, cleanRuleCode);

        setCacheList(tenantCode, cleanRuleCode, list);
        return list;
    }

    public int insertBatch(List<CleanRuleStepPO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (CleanRuleStepPO po : poList) {
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
        return "cleanRuleStepAcc-" + tenantCode + "-" + cleanRuleCode;
    }

    private List<CleanRuleStepPO> getCacheList(String tenantCode, String cleanRuleCode) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<CleanRuleStepPO> poList = (List<CleanRuleStepPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String cleanRuleCode, List<CleanRuleStepPO> poList) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String cleanRuleCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, cleanRuleCode));
    }
}
