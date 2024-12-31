package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface TenantMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<TenantDTO> get(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<TenantDTO>> search(String tenantCode, String contactPerson, int pageNum, int pageSize);

    /**
     *
     * @return
     */
    TeaMachineResult<List<TenantDTO>> list();

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(TenantPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<Void> delete(String tenantCode);
}
