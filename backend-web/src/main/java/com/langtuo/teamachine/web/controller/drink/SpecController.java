package com.langtuo.teamachine.web.controller.drink;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.SpecDTO;
import com.langtuo.teamachine.api.request.drink.SpecPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.drink.SpecMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/spec")
@Slf4j
public class SpecController {
    @Resource
    private SpecMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<SpecDTO> get(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("specCode") String specCode) {
        IceMachineResult<SpecDTO> rtn = service.getBySpecCode(tenantCode, specCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<SpecDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        log.debug("list|entering|tenantCode=" +  tenantCode);
        IceMachineResult<List<SpecDTO>> rtn = service.list(tenantCode);
        log.debug("list|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<SpecDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                     @RequestParam(name = "specCode", required = false) String specCode,
                                                     @RequestParam(name = "specName", required = false) String specName,
                                                     @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<SpecDTO>> rtn = service.search(tenantCode, specCode, specName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody SpecPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("specCode") String specCode) {
        IceMachineResult<Void> rtn = service.deleteBySpecCode(tenantCode, specCode);
        return rtn;
    }
}
