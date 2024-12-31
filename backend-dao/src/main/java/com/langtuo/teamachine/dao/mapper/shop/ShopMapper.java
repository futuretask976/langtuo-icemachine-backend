package com.langtuo.teamachine.dao.mapper.shop;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.query.shop.ShopQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShopMapper {
    /**
     *
     * @param tenantCode
     * @return
     */
    ShopPO selectOne(@Param("tenantCode") String tenantCode, @Param("shopCode") String shopCode);

    /**
     *
     * @return
     */
    List<ShopPO> selectListByShopGroupCodeList(@Param("tenantCode") String tenantCode,
            @Param("shopGroupCodeList") List<String> shopGroupCodeList);

    /**
     *
     * @param query
     * @return
     */
    List<ShopPO> search(ShopQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(ShopPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(ShopPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("shopCode") String shopCode);

    /**
     *
     * @param tenantCode
     * @param shopGroupCode
     * @return
     */
    int countByShopGroupCode(@Param("tenantCode") String tenantCode, @Param("shopGroupCode") String shopGroupCode);
}
