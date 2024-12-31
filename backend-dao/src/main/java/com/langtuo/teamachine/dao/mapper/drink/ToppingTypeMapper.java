package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.drink.ToppingTypePO;
import com.langtuo.teamachine.dao.query.drink.ToppingTypeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ToppingTypeMapper {
    /**
     *
     * @param tenantCode
     * @param toppingTypeCode
     * @return
     */
    ToppingTypePO selectOne(@Param("tenantCode") String tenantCode, @Param("toppingTypeCode") String toppingTypeCode);

    /**
     *
     * @return
     */
    List<ToppingTypePO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<ToppingTypePO> search(ToppingTypeQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(ToppingTypePO po);

    /**
     *
     * @param po
     * @return
     */
    int update(ToppingTypePO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("toppingTypeCode") String toppingTypeCode);
}
