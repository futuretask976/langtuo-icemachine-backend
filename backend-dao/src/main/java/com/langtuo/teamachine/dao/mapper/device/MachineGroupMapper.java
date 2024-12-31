package com.langtuo.teamachine.dao.mapper.device;

import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.query.device.MachineGroupQuery;
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
    MachineGroupPO selectOne(@Param("tenantCode") String tenantCode, @Param("machineGroupCode") String machineGroupCode);

    /**
     *
     * @return
     */
    List<MachineGroupPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @param query
     * @return
     */
    List<MachineGroupPO> search(MachineGroupQuery query);

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
    int delete(@Param("tenantCode") String tenantCode, @Param("machineGroupCode") String machineGroupCode);
}
