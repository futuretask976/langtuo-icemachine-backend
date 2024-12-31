package com.langtuo.teamachine.api.service.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.SpecDTO;
import com.langtuo.teamachine.api.request.drink.SpecPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface SpecMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<SpecDTO> getBySpecCode(String tenantCode, String specCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<SpecDTO>> search(String tenantCode, String specCode, String specName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<SpecDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(SpecPutRequest request);

    /**
     *
     * @param tenantCode
     * @param specCode
     * @return
     */
    TeaMachineResult<Void> deleteBySpecCode(String tenantCode, String specCode);
}
