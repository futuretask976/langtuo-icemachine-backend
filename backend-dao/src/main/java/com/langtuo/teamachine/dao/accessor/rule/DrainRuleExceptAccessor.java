package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.rule.DrainRuleExceptNewMapper;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DrainRuleExceptAccessor {
    @Resource
    private DrainRuleExceptNewMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<DrainRuleExceptPO> listByDrainRuleCode(String tenantCode, String drainRuleCode) {
        List<DrainRuleExceptPO> cached = getCacheList(tenantCode, drainRuleCode);
        if (cached != null) {
            return cached;
        }

        List<DrainRuleExceptPO> list = mapper.selectList(tenantCode, drainRuleCode);

        setCacheList(tenantCode, drainRuleCode, list);
        return list;
    }

    public int insertBatch(List<DrainRuleExceptPO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (DrainRuleExceptPO po : poList) {
                deleteCacheList(po.getTenantCode(), po.getDrainRuleCode());
            }
        }
        return inserted;
    }

    public int deleteByDrainRuleCode(String tenantCode, String drainRuleCode) {
        int deleted = mapper.delete(tenantCode, drainRuleCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheList(tenantCode, drainRuleCode);
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String cleanRuleCode) {
        return "drainRuleExceptAcc-" + tenantCode + "-" + cleanRuleCode;
    }

    private List<DrainRuleExceptPO> getCacheList(String tenantCode, String cleanRuleCode) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<DrainRuleExceptPO> poList = (List<DrainRuleExceptPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String cleanRuleCode, List<DrainRuleExceptPO> poList) {
        String key = getCacheListKey(tenantCode, cleanRuleCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String cleanRuleCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, cleanRuleCode));
    }
}
