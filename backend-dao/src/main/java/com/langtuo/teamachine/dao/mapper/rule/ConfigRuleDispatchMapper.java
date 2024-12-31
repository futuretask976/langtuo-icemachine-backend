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
            @Param("configRuleCode") String configRuleCode,
            @Param("machineGroupCodeList") List<String> machineGroupCodeList);

    /**
     *
     * @return
     */
    List<ConfigRuleDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
            @Param("machineGroupCode") String machineGroupCode);

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
     * @param configRuleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("configRuleCode") String configRuleCode,
            @Param("machineGroupCodeList") List<String> machineGroupCodeList);
}
