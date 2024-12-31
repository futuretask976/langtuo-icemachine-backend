package com.langtuo.teamachine.api.service.device;

import com.langtuo.teamachine.api.model.device.DeployDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.DeployPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface DeployMgtService {
    /**
     *
     * @param tenantCode
     * @param deployCode
     * @return
     */
    TeaMachineResult<DeployDTO> getByDeployCode(String tenantCode, String deployCode);

    /**
     *
     * @param tenantCode
     * @param machineCode
     * @return
     */
    TeaMachineResult<DeployDTO> getByMachineCode(String tenantCode, String machineCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<DeployDTO>> search(String tenantCode, String deployCode, String shopCode, Integer state,
            int pageNum, int pageSize);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(DeployPutRequest request);

    /**
     *
     * @param tenantCode
     * @param deployCode
     * @return
     */
    TeaMachineResult<Void> deleteByDeployCode(String tenantCode, String deployCode);

    /**
     *
     * @return
     */
    TeaMachineResult<String> generateDeployCode();

    /**
     *
     * @return
     */
    TeaMachineResult<String> generateMachineCode();

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<XSSFWorkbook> exportByExcel(String tenantCode);
}
