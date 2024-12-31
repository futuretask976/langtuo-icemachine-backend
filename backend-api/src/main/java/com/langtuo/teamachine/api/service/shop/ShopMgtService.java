package com.langtuo.teamachine.api.service.shop;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.shop.ShopDTO;
import com.langtuo.teamachine.api.request.shop.ShopPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface ShopMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<ShopDTO> getByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<ShopDTO>> search(String tenantCode, String shopName, String shopGroupCode, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<ShopDTO>> listByShopGroupCode(String tenantCode, String shopGroupCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(ShopPutRequest request);

    /**
     *
     * @param tenantCode
     * @param shopCode
     * @return
     */
    TeaMachineResult<Void> deleteByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param tenantCode
     * @param shopGroupCode
     * @return
     */
    TeaMachineResult<Integer> countByShopGroupCode(String tenantCode, String shopGroupCode);
}
