package com.langtuo.teamachine.web.controller.device;

import com.langtuo.teamachine.api.model.device.DeployDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.DeployPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.device.DeployMgtService;
import com.langtuo.teamachine.web.constant.WebConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.langtuo.teamachine.api.result.TeaMachineResult.getModel;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/deviceset/deploy")
@Slf4j
public class DeployController {
    @Resource
    private DeployMgtService service;

    @GetMapping(value = "/get")
    public TeaMachineResult<DeployDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("deployCode") String deployCode) {
        TeaMachineResult<DeployDTO> rtn = service.getByDeployCode(tenantCode, deployCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<DeployDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "deployCode", required = false) String deployCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam(name = "state", required = false) Integer state,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<DeployDTO>> rtn = service.search(tenantCode, deployCode, shopCode, state,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody DeployPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam(name = "tenantCode") String tenantCode,
            @RequestParam("deployCode") String deployCode) {
        TeaMachineResult<Void> rtn = service.deleteByDeployCode(tenantCode, deployCode);
        return rtn;
    }

    @GetMapping(value = "/deploycode/generate")
    public TeaMachineResult<String> generateDeployCode(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<String> rtn = service.generateDeployCode();
        return rtn;
    }

    @GetMapping(value = "/machinecode/generate")
    public TeaMachineResult<String> generateMachineCode(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<String> rtn = service.generateMachineCode();
        return rtn;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<XSSFWorkbook> rtn = service.exportByExcel(tenantCode);
        XSSFWorkbook xssfWorkbook = getModel(rtn);

        // 导出Excel文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
        } catch (IOException e) {
            log.error("exportExcel|fatal|e=" + e.getMessage(), e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(WebConsts.HTTP_HEADER_DISPOSITION_NAME,
                WebConsts.HTTP_HEADER_DISPOSITION_FILE_NAME_4_DEPLOY_EXPORT);

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }
}
