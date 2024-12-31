package com.langtuo.teamachine.api.service.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.AccuracyTplDTO;
import com.langtuo.teamachine.api.request.drink.AccuracyTplPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface AccuracyTplMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<AccuracyTplDTO> getByTplCode(String tenantCode, String templateCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<AccuracyTplDTO>> search(String tenantCode, String templateCode, String templateName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<AccuracyTplDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(AccuracyTplPutRequest request);

    /**
     *
     * @param tenantCode
     * @param templateCode
     * @return
     */
    TeaMachineResult<Void> deleteByTplCode(String tenantCode, String templateCode);
}
