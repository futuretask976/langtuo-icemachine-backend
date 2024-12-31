package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.user.RoleDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.RolePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface RoleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<RoleDTO> getByCode(String tenantCode, String roleCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<RoleDTO>> search(String tenantCode, String roleName, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<RoleDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(RolePutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<Void> delete(String tenantCode, String roleCode);
}
