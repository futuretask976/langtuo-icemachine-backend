package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.DrainRulePO;
import com.langtuo.teamachine.dao.query.rule.DrainRuleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrainRuleMapper {
    /**
     *
     * @param tenantCode
     * @param drainRuleCode
     * @return
     */
    DrainRulePO selectOne(@Param("tenantCode") String tenantCode, @Param("drainRuleCode") String drainRuleCode);

    /**
     *
     * @return
     */
    List<DrainRulePO> selectList(@Param("tenantCode") String tenantCode,
                                 @Param("drainRuleCodeList") List<String> drainRuleCodeList);

    /**
     *
     * @return
     */
    List<DrainRulePO> search(DrainRuleQuery query);

    /**
     *
     * @param DrainRulePO
     * @return
     */
    int insert(DrainRulePO DrainRulePO);

    /**
     *
     * @param DrainRulePO
     * @return
     */
    int update(DrainRulePO DrainRulePO);

    /**
     *
     * @param tenantCode
     * @param drainRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("drainRuleCode") String drainRuleCode);
}
