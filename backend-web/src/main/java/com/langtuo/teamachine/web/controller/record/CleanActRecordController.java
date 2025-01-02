package com.langtuo.teamachine.web.controller.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.ActRecordDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.record.ActRecordMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/recordset/clean")
public class CleanActRecordController {
    @Resource
    private ActRecordMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<ActRecordDTO> get(@RequestParam("tenantCode") String tenantCode,
                                              @RequestParam("idempotentMark") String idempotentMark) {
        IceMachineResult<ActRecordDTO> rtn = service.get(tenantCode, idempotentMark);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ActRecordDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                          @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
                                                          @RequestParam(name = "shopCode", required = false) String shopCode,
                                                          @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ActRecordDTO>> rtn = service.search(tenantCode, shopGroupCode, shopCode,
                pageNum, pageSize);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("idempotentMark") String idempotentMark) {
        IceMachineResult<Void> rtn = service.delete(tenantCode, idempotentMark);
        return rtn;
    }
}
