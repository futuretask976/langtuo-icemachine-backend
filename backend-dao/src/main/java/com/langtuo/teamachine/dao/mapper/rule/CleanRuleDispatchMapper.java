package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.CleanRuleDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CleanRuleDispatchMapper {
    /**
     *
     * @return
     */
    List<CleanRuleDispatchPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("cleanRuleCode") String cleanRuleCode, @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @return
     */
    List<CleanRuleDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<CleanRuleDispatchPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(CleanRuleDispatchPO po);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);
}
