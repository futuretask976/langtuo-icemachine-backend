package com.langtuo.teamachine.dao.mapper.rule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CleanRuleStepMapper {
    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @param stepIndex
     * @return
     */
    CleanRuleStepPO selectOne(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode,
            @Param("stepIndex") Integer stepIndex);

    /**
     *
     * @return
     */
    List<CleanRuleStepPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("cleanRuleCode") String cleanRuleCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<CleanRuleStepPO> poList);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);
}
