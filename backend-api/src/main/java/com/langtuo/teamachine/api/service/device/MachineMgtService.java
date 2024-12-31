package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface MachineMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<MachineDTO> getByMachineCode(String tenantCode, String machineCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<MachineDTO>> search(String tenantCode, String machineCode, String screenCode,
            String elecBoardCode, String shopCode, int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<MachineDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<MachineDTO>> listByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<MachineDTO> activate(MachineActivatePutRequest request);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(MachineUpdatePutRequest request);

    /**
     *
     * @param tenantCode
     * @param machineCode
     * @return
     */
    TeaMachineResult<Void> deleteByMachineCode(String tenantCode, String machineCode);
}
