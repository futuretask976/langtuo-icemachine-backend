package com.langtuo.teamachine.dao.mapper.rule;

import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConfigRuleDispatchMapper {
    /**
     *
     * @return
     */
    List<ConfigRuleDispatchPO> selectList(@Param("tenantCode") String tenantCode,
                                          @Param("cleanRuleCode") String cleanRuleCode, @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @return
     */
    List<ConfigRuleDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
                                                         @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<ConfigRuleDispatchPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(ConfigRuleDispatchPO po);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("cleanRuleCode") String cleanRuleCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);
}
