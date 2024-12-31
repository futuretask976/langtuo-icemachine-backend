package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.rule.CleanRulePO;
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
    CleanRulePO selectOne(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);

    /**
     *
     * @return
     */
    List<CleanRulePO> selectList(@Param("tenantCode") String tenantCode,
            @Param("cleanRuleCodeList") List<String> cleanRuleCodeList);

    /**
     *
     * @return
     */
    List<CleanRulePO> search(CleanRuleQuery query);

    /**
     *
     * @param cleanRulePO
     * @return
     */
    int insert(CleanRulePO cleanRulePO);

    /**
     *
     * @param cleanRulePO
     * @return
     */
    int update(CleanRulePO cleanRulePO);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);
}
