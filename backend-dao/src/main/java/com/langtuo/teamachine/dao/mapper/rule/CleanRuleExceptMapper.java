package com.langtuo.teamachine.dao.mapper.rule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CleanRuleExceptMapper {
    /**
     *
     * @return
     */
    List<CleanRuleExceptPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("cleanRuleCode") String cleanRuleCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<CleanRuleExceptPO> poList);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode);
}
