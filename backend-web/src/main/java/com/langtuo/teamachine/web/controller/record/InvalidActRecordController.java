package com.langtuo.teamachine.web.controller.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/recordset/invalid")
public class InvalidActRecordController {
    @Resource
    private InvalidActRecordMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<InvalidActRecordDTO> get(@RequestParam(name = "tenantCode") String tenantCode,
                                                     @RequestParam(name = "idempotentMark") String idempotentMark) {
        IceMachineResult<InvalidActRecordDTO> rtn = service.get(tenantCode, idempotentMark);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<InvalidActRecordDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                                 @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
                                                                 @RequestParam(name = "shopCode", required = false) String shopCode,
                                                                 @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<InvalidActRecordDTO>> rtn = service.search(tenantCode, shopGroupCode, shopCode,
                pageNum, pageSize);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam(name = "tenantCode") String tenantCode,
                                         @RequestParam(name = "idempotentMark") String idempotentMark) {
        IceMachineResult<Void> rtn = service.delete(tenantCode, idempotentMark);
        return rtn;
    }
}
