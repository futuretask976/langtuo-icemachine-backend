package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.request.user.AdminPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

public interface AdminMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<AdminDTO> getByLoginName(String tenantCode, String loginName);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<AdminDTO>> search(String tenantCode, String loginName, String roleName,
                                               int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    // TeaMachineResult<List<AdminDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(AdminPutRequest request);

    /**
     *
     * @param tenantCode
     * @param loginName
     * @return
     */
    IceMachineResult<Void> deleteByLoginName(String tenantCode, String loginName);

    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    IceMachineResult<Integer> countByRoleCode(String tenantCode, String roleCode);
}
