package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.DrainRuleDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrainRuleDispatchMapper {
    /**
     *
     * @return
     */
    List<DrainRuleDispatchPO> selectList(@Param("tenantCode") String tenantCode,
                                         @Param("drainRuleCode") String drainRuleCode, @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @return
     */
    List<DrainRuleDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
                                                        @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<DrainRuleDispatchPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(DrainRuleDispatchPO po);

    /**
     *
     * @param tenantCode
     * @param drainRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("drainRuleCode") String drainRuleCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);
}
