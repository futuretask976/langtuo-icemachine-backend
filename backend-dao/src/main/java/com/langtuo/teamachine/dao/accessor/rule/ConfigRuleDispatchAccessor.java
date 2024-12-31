package com.langtuo.teamachine.dao.accessor.rule;

import com.langtuo.teamachine.dao.mapper.rule.ConfigRuleDispatchMapper;
import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ConfigRuleDispatchAccessor {
    @Resource
    private ConfigRuleDispatchMapper mapper;

    public List<ConfigRuleDispatchPO> listByCleanRuleCode(String tenantCode, String cleanRuleCode,
                                                          List<String> shopGroupCodeList) {
        List<ConfigRuleDispatchPO> list = mapper.selectList(tenantCode, cleanRuleCode, shopGroupCodeList);
        return list;
    }

    public List<ConfigRuleDispatchPO> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<ConfigRuleDispatchPO> list = mapper.selectListByShopGroupCode(tenantCode, shopGroupCode);
        return list;
    }

    public int insertBatch(List<ConfigRuleDispatchPO> poList) {
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
