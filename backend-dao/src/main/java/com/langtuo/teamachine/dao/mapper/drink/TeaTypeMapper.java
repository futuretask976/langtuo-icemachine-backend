package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.drink.TeaTypePO;
import com.langtuo.teamachine.dao.query.drink.TeaTypeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeaTypeMapper {
    /**
     *
     * @param tenantCode
     * @param teaTypeCode
     * @return
     */
    TeaTypePO selectOne(@Param("tenantCode") String tenantCode, @Param("teaTypeCode") String teaTypeCode);

    /**
     *
     * @return
     */
    List<TeaTypePO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<TeaTypePO> search(TeaTypeQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(TeaTypePO po);

    /**
     *
     * @param po
     * @return
     */
    int update(TeaTypePO po);

    /**
     *
     * @param tenantCode
     * @param teaTypeCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("teaTypeCode") String teaTypeCode);
}
