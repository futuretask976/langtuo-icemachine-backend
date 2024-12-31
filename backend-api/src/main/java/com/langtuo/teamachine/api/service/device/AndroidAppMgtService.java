package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDispatchDTO;
import com.langtuo.teamachine.api.request.device.AndroidAppDispatchPutRequest;
import com.langtuo.teamachine.api.request.device.AndroidAppPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface AndroidAppMgtService {
    /**
     *
     * @return
     */
    IceMachineResult<List<AndroidAppDTO>> listByLimit(int limit);

    /**
     *
     * @param version
     * @return
     */
    IceMachineResult<AndroidAppDTO> getByVersion(String version);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<AndroidAppDTO>> search(String version, int pageNum, int pageSize);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(AndroidAppPutRequest request);

    /**
     *
     * @param version
     * @return
     */
    IceMachineResult<Void> delete(String tenantCode, String version);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> putDispatch(AndroidAppDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<AndroidAppDispatchDTO> getDispatchByVersion(String tenantCode, String version);
}
