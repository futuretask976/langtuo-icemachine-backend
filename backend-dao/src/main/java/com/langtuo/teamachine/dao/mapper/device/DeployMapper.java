package com.langtuo.teamachine.dao.mapper.device;

import com.langtuo.teamachine.dao.query.device.MachineDeployQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeployMapper {
    /**
     *
     * @param deployCode
     * @return
     */
    DeployPO selectOne(@Param("tenantCode") String tenantCode, @Param("deployCode") String deployCode,
            @Param("machineCode") String machineCode);

    /**
     *
     * @return
     */
    List<DeployPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<DeployPO> search(MachineDeployQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(DeployPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(DeployPO po);

    /**
     * 
     * @param tenantCode
     * @param deployCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("deployCode") String deployCode);

    /**
     *
     * @param modelCode
     * @return
     */
    int countByModelCode(@Param("modelCode") String modelCode);
}
