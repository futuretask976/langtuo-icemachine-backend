package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDispatchDTO;
import com.langtuo.teamachine.api.request.device.AndroidAppDispatchPutRequest;
import com.langtuo.teamachine.api.request.device.AndroidAppPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface AndroidAppMgtService {
    /**
     *
     * @return
     */
    TeaMachineResult<List<AndroidAppDTO>> listByLimit(int limit);

    /**
     *
     * @param version
     * @return
     */
    TeaMachineResult<AndroidAppDTO> getByVersion(String version);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<AndroidAppDTO>> search(String version, int pageNum, int pageSize);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(AndroidAppPutRequest request);

    /**
     *
     * @param version
     * @return
     */
    TeaMachineResult<Void> delete(String tenantCode, String version);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> putDispatch(AndroidAppDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<AndroidAppDispatchDTO> getDispatchByVersion(String tenantCode, String version);
}
