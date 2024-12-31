package com.langtuo.teamachine.dao.mapper.rule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrainRuleExceptNewMapper {
    /**
     *
     * @return
     */
    List<DrainRuleExceptPO> selectList(@Param("tenantCode") String tenantCode,
                                       @Param("drainRuleCode") String drainRuleCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<DrainRuleExceptPO> poList);

    /**
     *
     * @param tenantCode
     * @param drainRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("drainRuleCode") String drainRuleCode);
}
