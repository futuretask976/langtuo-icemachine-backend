package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.ConfigRulePO;
import com.langtuo.teamachine.dao.query.rule.CleanRuleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CleanRuleMapper {
    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    ConfigRulePO selectOne(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);

    /**
     *
     * @return
     */
    List<ConfigRulePO> selectList(@Param("tenantCode") String tenantCode,
                                  @Param("cleanRuleCodeList") List<String> cleanRuleCodeList);

    /**
     *
     * @return
     */
    List<ConfigRulePO> search(CleanRuleQuery query);

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
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);
}
