package com.langtuo.teamachine.dao.mapper.shop;

import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.query.shop.ShopGroupQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MachineGroupMapper {
    /**
     *
     * @param tenantCode
     * @return
     */
    MachineGroupPO selectOne(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @return
     */
    List<MachineGroupPO> selectListByOrgNameList(@Param("tenantCode") String tenantCode,
                                                 @Param("orgNameList") List<String> orgNameList);

    /**
     *
     * @param query
     * @return
     */
    List<MachineGroupPO> search(ShopGroupQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(MachineGroupPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(MachineGroupPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param tenantCode
     * @param orgName
     * @return
     */
    int countByOrgName(@Param("tenantCode") String tenantCode, @Param("orgName") String orgName);
}
