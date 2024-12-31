package com.langtuo.teamachine.dao.accessor.drink;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.drink.ToppingAdjustRuleMapper;
import com.langtuo.teamachine.dao.po.drink.ToppingAdjustRulePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToppingAdjustRuleAccessor {
    @Resource
    private ToppingAdjustRuleMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<ToppingAdjustRulePO> listByTeaUnitCode(String tenantCode, String teaCode, String teaUnitCode) {
        List<ToppingAdjustRulePO> cached = getCacheList(tenantCode, teaCode, teaUnitCode);
        if (!CollectionUtils.isEmpty(cached)) {
            return cached;
        }

        List<ToppingAdjustRulePO> list = mapper.selectList(tenantCode, teaCode, teaUnitCode);

        setCacheList(tenantCode, teaCode, teaUnitCode, list);
        return list;
    }

    public int insertBatch(List<ToppingAdjustRulePO> poList) {
        int inserted = mapper.insertBatch(poList);
        if (inserted == poList.size()) {
            for (ToppingAdjustRulePO po : poList) {
                deleteCacheList(po.getTenantCode(), po.getTeaCode(), po.getTeaUnitCode());
            }
        }
        return inserted;
    }

    public int deleteByTeaCode(String tenantCode, String teaCode) {
        List<ToppingAdjustRulePO> poList = mapper.selectList(tenantCode, teaCode, null);
        List<String> teaUnitCodeList = poList.stream()
                .map(po -> po.getTeaUnitCode())
                .collect(Collectors.toList());

        int deleted = mapper.delete(tenantCode, teaCode);
        if (deleted > CommonConsts.DB_DELETED_ZERO_ROW) {
            for (String teaUnitCode : teaUnitCodeList) {
                deleteCacheList(tenantCode, teaCode, teaUnitCode);
            }
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String teaCode, String teaUnitCode) {
        return "toppingAdjustRuleAcc-" + tenantCode + "-" + teaCode + "-" + teaUnitCode;
    }

    private List<ToppingAdjustRulePO> getCacheList(String tenantCode, String teaCode, String teaUnitCode) {
        String key = getCacheListKey(tenantCode, teaCode, teaUnitCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ToppingAdjustRulePO> poList = (List<ToppingAdjustRulePO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String teaCode, String teaUnitCode, List<ToppingAdjustRulePO> poList) {
        String key = getCacheListKey(tenantCode, teaCode, teaUnitCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String teaCode, String teaUnitCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, teaCode, teaUnitCode));
    }
}
