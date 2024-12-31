package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface TenantMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<TenantDTO> get(String tenantCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<TenantDTO>> search(String tenantCode, String contactPerson, int pageNum, int pageSize);

    /**
     *
     * @return
     */
    IceMachineResult<List<TenantDTO>> list();

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(TenantPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<Void> delete(String tenantCode);
}
