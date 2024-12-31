package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.po.menu.MenuDispatchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDispatchMapper {
    /**
     *
     * @return
     */
    List<MenuDispatchPO> selectList(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @return
     */
    List<MenuDispatchPO> selectListByShopGroupCode(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCode") String shopGroupCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<MenuDispatchPO> poList);

    /**
     *
     * @param po
     * @return
     */
    int update(MenuDispatchPO po);

    /**
     *
     * @param tenantCode
     * @param menuCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);
}
