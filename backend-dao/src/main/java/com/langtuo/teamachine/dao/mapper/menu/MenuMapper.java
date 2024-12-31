package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.query.menu.MenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper {
    /**
     *
     * @param tenantCode
     * @return
     */
    MenuPO selectOne(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode);

    /**
     *
     * @return
     */
    List<MenuPO> selectList(@Param("tenantCode") String tenantCode,
            @Param("menuCodeList") List<String> menuCodeList);

    /**
     *
     * @return
     */
    List<MenuPO> search(MenuQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(MenuPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(MenuPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("menuCode") String menuCode);
}
