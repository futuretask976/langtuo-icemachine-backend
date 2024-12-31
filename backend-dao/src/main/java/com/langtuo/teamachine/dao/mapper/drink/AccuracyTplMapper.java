package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.po.drink.AccuracyTplPO;
import com.langtuo.teamachine.dao.query.drink.AccuracyTplQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccuracyTplMapper {
    /**
     *
     * @param tenantCode
     * @param templateCode
     * @return
     */
    AccuracyTplPO selectOne(@Param("tenantCode") String tenantCode, @Param("templateCode") String templateCode);

    /**
     *
     * @return
     */
    List<AccuracyTplPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<AccuracyTplPO> search(AccuracyTplQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(AccuracyTplPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(AccuracyTplPO po);

    /**
     *
     * @param templateCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("templateCode") String templateCode);
}
