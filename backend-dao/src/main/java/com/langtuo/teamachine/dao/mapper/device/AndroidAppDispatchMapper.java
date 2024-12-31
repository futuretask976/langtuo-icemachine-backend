package com.langtuo.teamachine.dao.mapper.device;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.device.AndroidAppDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AndroidAppDispatchMapper {
    /**
     *
     * @param tenantCode
     * @param version
     * @param shopGroupCode
     * @return
     */
    AndroidAppDispatchPO selectOne(@Param("tenantCode") String tenantCode, @Param("version") String version,
            @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @return
     */
    List<AndroidAppDispatchPO> selectListByVersion(@Param("tenantCode") String tenantCode, @Param("version") String version);

    /**
     *
     * @return
     */
    List<AndroidAppDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param menuDispatchPO
     * @return
     */
    int insert(AndroidAppDispatchPO menuDispatchPO);

    /**
     *
     * @param menuDispatchPO
     * @return
     */
    int update(AndroidAppDispatchPO menuDispatchPO);

    /**
     *
     * @param tenantCode
     * @param version
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("version") String version);
}
