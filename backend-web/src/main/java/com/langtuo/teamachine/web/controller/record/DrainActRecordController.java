package com.langtuo.teamachine.web.controller.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.DrainActRecordDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.record.DrainActRecordMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/recordset/drain")
public class DrainActRecordController {
    @Resource
    private DrainActRecordMgtService service;
    
    @GetMapping(value = "/get")
    public TeaMachineResult<DrainActRecordDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("idempotentMark") String idempotentMark) {
        TeaMachineResult<DrainActRecordDTO> rtn = service.get(tenantCode, idempotentMark);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<DrainActRecordDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<DrainActRecordDTO>> rtn = service.search(tenantCode, shopGroupCode, shopCode,
                pageNum, pageSize);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("idempotentMark") String idempotentMark) {
        TeaMachineResult<Void> rtn = service.delete(tenantCode, idempotentMark);
        return rtn;
    }
}
