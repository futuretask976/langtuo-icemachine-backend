package com.langtuo.teamachine.dao.mapper.device;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.query.device.MachineQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MachineMapper {
    /**
     *
     * @param tenantCode
     * @param machineCode
     * @return
     */
    MachinePO selectOne(@Param("tenantCode") String tenantCode, @Param("machineCode") String machineCode);

    /**
     *
     * @return
     */
    List<MachinePO> selectList(@Param("tenantCode") String tenantCode, @Param("shopCode") String shopCode);

    /**
     *
     * @param query
     * @return
     */
    List<MachinePO> search(MachineQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(MachinePO po);

    /**
     *
     * @param po
     * @return
     */
    int update(MachinePO po);

    /**
     *
     * @param tenantCode
     * @param machineCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("machineCode") String machineCode);

    /**
     *
     * @param modelCode
     * @return
     */
    int countByModelCode(@Param("modelCode") String modelCode);

    /**
     *
     * @param machineCode
     * @return
     */
    int updateOnlineStateByMachineCode(@Param("machineCode") String machineCode, @Param("onlineState") int onlineState);
}
