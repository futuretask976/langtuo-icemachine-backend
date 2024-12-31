package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.po.menu.MenuSeriesRelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuSeriesRelMapper {
    /**
     *
     * @param tenantCode
     * @param seriesCode
     * @param menuCode
     * @return
     */
    MenuSeriesRelPO selectOne(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode,
            @Param("seriesCode") String seriesCode);

    /**
     *
     * @return
     */
    List<MenuSeriesRelPO> selectList(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<MenuSeriesRelPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(MenuSeriesRelPO po);

    /**
     *
     * @param tenantCode
     * @param menuCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode);

    /**
     *
     * @param tenantCode
     * @param seriesCode
     * @return
     */
    int countBySeriesCode(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode);
}
