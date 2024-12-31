package com.langtuo.teamachine.web.controller.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.TeaDTO;
import com.langtuo.teamachine.api.request.drink.TeaPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.TeaMgtService;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import com.langtuo.teamachine.web.constant.WebConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.langtuo.teamachine.api.result.TeaMachineResult.getModel;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/tea")
@Slf4j
public class TeaController {
    @Resource
    private TeaMgtService service;

    @GetMapping(value = "/get")
    public TeaMachineResult<TeaDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("teaCode") String teaCode) {
        TeaMachineResult<TeaDTO> rtn = service.getByTeaCode(tenantCode, teaCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public TeaMachineResult<List<TeaDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<TeaDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<TeaDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "teaCode", required = false) String teaCode,
            @RequestParam(name = "teaName", required = false) String teaName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<TeaDTO>> rtn = service.search(tenantCode, teaCode, teaName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody TeaPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("teaCode") String teaCode) {
        TeaMachineResult<Void> rtn = service.deleteByTeaCode(tenantCode, teaCode);
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
                WebConsts.HTTP_HEADER_DISPOSITION_FILE_NAME_4_TEA_EXPORT);

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

    @PostMapping("/upload")
    public TeaMachineResult<Void> uploadExcel(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_UPLOAD_FILE_IS_EMPTY));
        }

        // 获取文件的字节
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(file.getBytes());
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            TeaMachineResult<Void> uploadResult = service.importByExcel(tenantCode, workbook);
            return uploadResult;
        } catch (IOException e) {
            log.error("uploadExcel|fatal|e=" + e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("uploadExcel|fatal|e=" + e.getMessage(), e);
                }
            }
        }
        return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_PARSE_UPLOAD_FILE_ERROR));
    }
}
