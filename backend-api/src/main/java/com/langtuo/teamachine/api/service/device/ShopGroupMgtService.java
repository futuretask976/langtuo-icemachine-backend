package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.ShopGroupDTO;
import com.langtuo.teamachine.api.request.device.ShopGroupPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface ShopGroupMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<ShopGroupDTO> getByShopGroupCode(String tenantCode, String shopGroupCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<ShopGroupDTO>> search(String tenantCode, String shopGroupName, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<ShopGroupDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(ShopGroupPutRequest request);

    /**
     *
     * @param tenantCode
     * @param shopGroupCode
     * @return
     */
    IceMachineResult<Void> deleteByShopGroupCode(String tenantCode, String shopGroupCode);
}
