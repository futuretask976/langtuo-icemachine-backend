package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchCachePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDispatchCacheMapper {
    /**
     *
     * @param tenantCode
     * @param init
     * @param fileName
     * @return
     */
    MenuDispatchCachePO selectOne(@Param("tenantCode") String tenantCode, @Param("init") int init,
                                  @Param("fileName") String fileName);

    /**
     *
     * @param po
     * @return
     */
    int insert(MenuDispatchCachePO po);

    /**
     *
     * @param tenantCode
     * @param init
     * @param fileName
     * @return
     */
    int deleteByFileName(@Param("tenantCode") String tenantCode, @Param("init") int init,
                         @Param("fileName") String fileName);

    /**
     *
     * @param tenantCode
     * @param init
     * @param fileNameList
     * @return
     */
    int deleteByFileNameList(@Param("tenantCode") String tenantCode, @Param("init") int init,
               @Param("fileNameList") List<String> fileNameList);

    /**
     *
     * @param tenantCode
     * @return
     */
    int clear(@Param("tenantCode") String tenantCode);
}
