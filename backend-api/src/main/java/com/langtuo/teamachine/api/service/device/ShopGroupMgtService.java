package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.MachineGroupDTO;
import com.langtuo.teamachine.api.request.device.ShopGroupPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface ShopGroupMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<MachineGroupDTO> getByShopGroupCode(String tenantCode, String shopGroupCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<MachineGroupDTO>> search(String tenantCode, String shopGroupName, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<MachineGroupDTO>> list(String tenantCode);

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
