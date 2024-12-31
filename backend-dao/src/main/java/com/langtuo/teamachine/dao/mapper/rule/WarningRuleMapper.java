package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.query.rule.WarningRuleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WarningRuleMapper {
    /**
     *
     * @param tenantCode
     * @param warningRuleCode
     * @return
     */
    WarningRulePO selectOne(@Param("tenantCode") String tenantCode, @Param("warningRuleCode") String warningRuleCode);

    /**
     *
     * @return
     */
    List<WarningRulePO> selectList(@Param("tenantCode") String tenantCode,
            @Param("warningRuleCodeList") List<String> warningRuleCodeList);

    /**
     *
     * @return
     */
    List<WarningRulePO> search(WarningRuleQuery query);

    /**
     *
     * @param warningRulePO
     * @return
     */
    int insert(WarningRulePO warningRulePO);

    /**
     *
     * @param warningRulePO
     * @return
     */
    int update(WarningRulePO warningRulePO);

    /**
     *
     * @param tenantCode
     * @param warningRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("warningRuleCode") String warningRuleCode);
}
