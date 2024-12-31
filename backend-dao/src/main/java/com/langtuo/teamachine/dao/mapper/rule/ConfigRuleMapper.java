package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.ConfigRulePO;
import com.langtuo.teamachine.dao.query.rule.ConfigRuleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConfigRuleMapper {
    /**
     *
     * @param tenantCode
     * @param configRuleCode
     * @return
     */
    ConfigRulePO selectOne(@Param("tenantCode") String tenantCode, @Param("configRuleCode") String configRuleCode);

    /**
     *
     * @return
     */
    List<ConfigRulePO> selectList(@Param("tenantCode") String tenantCode,
            @Param("configRuleCodeList") List<String> configRuleCodeList);

    /**
     *
     * @return
     */
    List<ConfigRulePO> search(ConfigRuleQuery query);

    /**
     *
     * @param configRulePO
     * @return
     */
    int insert(ConfigRulePO configRulePO);

    /**
     *
     * @param configRulePO
     * @return
     */
    int update(ConfigRulePO configRulePO);

    /**
     *
     * @param tenantCode
     * @param configRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("configRuleCode") String configRuleCode);
}
