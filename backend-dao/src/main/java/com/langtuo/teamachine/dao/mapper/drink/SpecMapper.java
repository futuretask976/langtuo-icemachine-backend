package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.drink.SpecPO;
import com.langtuo.teamachine.dao.query.drink.SpecQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecMapper {
    /**
     *
     * @param tenantCode
     * @param specCode
     * @return
     */
    SpecPO selectOne(@Param("tenantCode") String tenantCode, @Param("specCode") String specCode);

    /**
     *
     * @return
     */
    List<SpecPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<SpecPO> search(SpecQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(SpecPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(SpecPO po);

    /**
     *
     * @param specCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("specCode") String specCode);
}
