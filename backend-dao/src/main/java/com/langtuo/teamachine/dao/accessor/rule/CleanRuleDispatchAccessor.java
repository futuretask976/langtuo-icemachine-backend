package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.mapper.rule.CleanRuleDispatchMapper;
import com.langtuo.teamachine.dao.po.rule.CleanRuleDispatchPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CleanRuleDispatchAccessor {
    @Resource
    private CleanRuleDispatchMapper mapper;

    public List<CleanRuleDispatchPO> listByCleanRuleCode(String tenantCode, String cleanRuleCode,
            List<String> shopGroupCodeList) {
        List<CleanRuleDispatchPO> list = mapper.selectList(tenantCode, cleanRuleCode, shopGroupCodeList);
        return list;
    }

    public List<CleanRuleDispatchPO> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<CleanRuleDispatchPO> list = mapper.selectListByShopGroupCode(tenantCode, shopGroupCode);
        return list;
    }

    public int insertBatch(List<CleanRuleDispatchPO> poList) {
        int inserted = mapper.insertBatch(poList);
        return inserted;
    }

    public int deleteByCleanRuleCode(String tenantCode, String cleanRuleCode, List<String> shopGroupCodeList) {
        int deleted = mapper.delete(tenantCode, cleanRuleCode, shopGroupCodeList);
        return deleted;
    }

    public int deleteAllByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        int deleted = mapper.delete(tenantCode, cleanRuleCode, null);
        return deleted;
    }
}
