package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.drink.TeaPO;
import com.langtuo.teamachine.dao.query.drink.TeaQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeaMapper {
    /**
     *
     * @param tenantCode
     * @param teaCode
     * @return
     */
    TeaPO selectOne(@Param("tenantCode") String tenantCode, @Param("teaCode") String teaCode);

    /**
     *
     * @return
     */
    List<TeaPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<TeaPO> search(TeaQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(TeaPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(TeaPO po);

    /**
     *
     * @param tenantCode
     * @param teaCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("teaCode") String teaCode);

    /**
     *
     * @param tenantCode
     * @param teaTypeCode
     * @return
     */
    int countByTeaTypeCode(@Param("tenantCode") String tenantCode, @Param("teaTypeCode") String teaTypeCode);
}
