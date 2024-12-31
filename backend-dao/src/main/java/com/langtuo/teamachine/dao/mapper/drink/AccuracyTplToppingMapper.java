package com.langtuo.teamachine.dao.mapper.drink;

import com.langtuo.teamachine.dao.po.drink.AccuracyTplToppingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccuracyTplToppingMapper {
    /**
     *
     * @return
     */
    List<AccuracyTplToppingPO> selectList(@Param("tenantCode") String tenantCode, @Param("templateCode") String templateCode);

    /**
     *
     * @param poList
     * @return
     */
    int insertBatch(List<AccuracyTplToppingPO> poList);

    /**
     *
     * @param templateCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("templateCode") String templateCode);
}
