package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.mapper.rule.DrainRuleDispatchMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DrainRuleDispatchAccessor {
    @Resource
    private DrainRuleDispatchMapper mapper;

    public List<DrainRuleDispatchPO> listByDrainRuleCode(String tenantCode, String drainRuleCode,
                                                         List<String> shopGroupCodeList) {
        List<DrainRuleDispatchPO> list = mapper.selectList(tenantCode, drainRuleCode, shopGroupCodeList);
        return list;
    }

    public List<DrainRuleDispatchPO> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<DrainRuleDispatchPO> list = mapper.selectListByShopGroupCode(tenantCode, shopGroupCode);
        return list;
    }

    public int insertBatch(List<DrainRuleDispatchPO> poList) {
        int inserted = mapper.insertBatch(poList);
        return inserted;
    }

    public int deleteByDrainRuleCode(String tenantCode, String drainRuleCode, List<String> shopGroupCodeList) {
        int deleted = mapper.delete(tenantCode, drainRuleCode, shopGroupCodeList);
        return deleted;
    }

    public int deleteAllByDrainRuleCode(String tenantCode, String drainRuleCode) {
        int deleted = mapper.delete(tenantCode, drainRuleCode, null);
        return deleted;
    }
}
