package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.request.user.AdminPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

public interface AdminMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<AdminDTO> getByLoginName(String tenantCode, String loginName);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<AdminDTO>> search(String tenantCode, String loginName, String roleName,
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
    TeaMachineResult<Void> put(AdminPutRequest request);

    /**
     *
     * @param tenantCode
     * @param loginName
     * @return
     */
    TeaMachineResult<Void> deleteByLoginName(String tenantCode, String loginName);

    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    TeaMachineResult<Integer> countByRoleCode(String tenantCode, String roleCode);
}
