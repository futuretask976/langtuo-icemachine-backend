package com.langtuo.teamachine.web.controller.drink;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingDTO;
import com.langtuo.teamachine.api.request.drink.ToppingPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.drink.ToppingMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/topping")
@Slf4j
public class ToppingController {
    @Resource
    private ToppingMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<ToppingDTO> get(@RequestParam("tenantCode") String tenantCode,
                                            @RequestParam("toppingCode") String toppingCode) {
        IceMachineResult<ToppingDTO> rtn = service.getByToppingCode(tenantCode, toppingCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<ToppingDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        log.debug("list|entering|tenantCode=" +  tenantCode);
        IceMachineResult<List<ToppingDTO>> rtn = service.list(tenantCode);
        log.debug("list|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ToppingDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                        @RequestParam(name = "toppingCode", required = false) String toppingCode,
                                                        @RequestParam(name = "toppingName", required = false) String toppingName,
                                                        @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ToppingDTO>> rtn = service.search(tenantCode, toppingCode, toppingName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody ToppingPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("toppingCode") String toppingCode) {
        IceMachineResult<Void> rtn = service.deleteByToppingCode(tenantCode, toppingCode);
        return rtn;
    }
}
