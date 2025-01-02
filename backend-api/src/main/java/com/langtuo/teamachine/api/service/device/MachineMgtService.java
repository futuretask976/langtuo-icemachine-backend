package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface MachineMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<MachineDTO> getByMachineCode(String tenantCode, String machineCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<MachineDTO>> search(String tenantCode, String machineCode, String machineGroupCode,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<MachineDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<MachineDTO>> listByMachineGroupCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<MachineDTO> activate(MachineActivatePutRequest request);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(MachineUpdatePutRequest request);

    /**
     *
     * @param tenantCode
     * @param machineCode
     * @return
     */
    IceMachineResult<Void> deleteByMachineCode(String tenantCode, String machineCode);
}
