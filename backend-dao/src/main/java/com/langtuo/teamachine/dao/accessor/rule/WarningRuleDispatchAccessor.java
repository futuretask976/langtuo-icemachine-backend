package com.langtuo.teamachine.dao.accessor.rule;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WarningRuleDispatchAccessor {
    @Resource
    private WarningRuleDispatchMapper mapper;

    public List<WarningRuleDispatchPO> listByWarningRuleCode(String tenantCode, String warningRuleCode,
            List<String> shopGroupCodeList) {
        List<WarningRuleDispatchPO> list = mapper.selectList(tenantCode, warningRuleCode, shopGroupCodeList);
        return list;
    }

    public List<WarningRuleDispatchPO> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<WarningRuleDispatchPO> list = mapper.selectListByShopGroupCode(tenantCode, shopGroupCode);
        return list;
    }

    public int insertBatch(List<WarningRuleDispatchPO> poList) {
        int inserted = mapper.insertBatch(poList);
        return inserted;
    }

    public int deleteByWarningRuleCode(String tenantCode, String warningRuleCode, List<String> shopGroupCodeList) {
        int deleted = mapper.delete(tenantCode, warningRuleCode, shopGroupCodeList);
        return deleted;
    }

    public int deleteAllByWarningRuleCode(String tenantCode, String warningRuleCode) {
        int deleted = mapper.delete(tenantCode, warningRuleCode, null);
        return deleted;
    }
}
