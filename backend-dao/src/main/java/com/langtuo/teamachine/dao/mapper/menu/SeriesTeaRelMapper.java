package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.po.menu.SeriesTeaRelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeriesTeaRelMapper {
    /**
     *
     * @param tenantCode
     * @param teaCode
     * @param seriesCode
     * @return
     */
    SeriesTeaRelPO selectOne(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode,
            @Param("teaCode") String teaCode);

    /**
     *
     * @return
     */
    List<SeriesTeaRelPO> selectList(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<SeriesTeaRelPO> poList);

    /**
     *
     * @param tenantCode
     * @param seriesCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode);

    /**
     *
     * @param tenantCode
     * @param teaCode
     * @return
     */
    int countByTeaCode(@Param("tenantCode") String tenantCode, @Param("teaCode") String teaCode);
}
