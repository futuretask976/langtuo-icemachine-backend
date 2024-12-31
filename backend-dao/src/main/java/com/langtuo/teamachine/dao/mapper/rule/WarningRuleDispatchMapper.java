package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.WarningRuleDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WarningRuleDispatchMapper {
    /**
     *
     * @return
     */
    List<WarningRuleDispatchPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("warningRuleCode") String warningRuleCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @return
     */
    List<WarningRuleDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<WarningRuleDispatchPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(WarningRuleDispatchPO po);

    /**
     *
     * @param tenantCode
     * @param warningRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("warningRuleCode") String warningRuleCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);
}
