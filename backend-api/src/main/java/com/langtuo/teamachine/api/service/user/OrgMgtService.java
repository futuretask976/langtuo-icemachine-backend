package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.user.OrgDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.OrgPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface OrgMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<OrgDTO> getByOrgName(String tenantCode, String orgName);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<OrgDTO> getTop(String tenantCode);

    /**
     *
     * @param orgName
     * @return
     */
    TeaMachineResult<List<OrgDTO>> listByParentOrgName(String tenantCode, String orgName);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<OrgDTO>> list(String tenantCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<OrgDTO>> search(String tenantCode, String contactPerson, int pageNum, int pageSize);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(OrgPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<Void> deleteByOrgName(String tenantCode, String orgName);
}
