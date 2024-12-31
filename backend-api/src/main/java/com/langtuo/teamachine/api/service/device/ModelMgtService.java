package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.device.ModelDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.ModelPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface ModelMgtService {
    /**
     *
     * @param modelCode
     * @return
     */
    TeaMachineResult<ModelDTO> getByModelCode(String modelCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<ModelDTO>> search(String modelCode, int pageNum, int pageSize);

    /**
     *
     * @return
     */
    TeaMachineResult<List<ModelDTO>> list();

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(ModelPutRequest request);

    /**
     *
     * @param modelCode
     * @return
     */
    TeaMachineResult<Void> deleteByModelCode(String modelCode);
}
