package com.langtuo.teamachine.dao.mapper.menu;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.menu.SeriesPO;
import com.langtuo.teamachine.dao.query.menu.SeriesQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeriesMapper {
    /**
     *
     * @param tenantCode
     * @return
     */
    SeriesPO selectOne(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode);

    /**
     *
     * @return
     */
    List<SeriesPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<SeriesPO> search(SeriesQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(SeriesPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(SeriesPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("seriesCode") String seriesCode);
}
