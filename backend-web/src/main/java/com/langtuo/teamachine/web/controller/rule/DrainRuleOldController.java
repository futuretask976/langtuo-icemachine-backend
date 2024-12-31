package com.langtuo.teamachine.web.controller.rule;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/ruleset/drain")
@Slf4j
public class DrainRuleOldController {
    @Resource
    private DrainRuleMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<DrainRuleNewDTO> get(@RequestParam("tenantCode") String tenantCode,
                                                 @RequestParam("drainRuleCode") String drainRuleCode) {
        IceMachineResult<DrainRuleNewDTO> rtn = service.getByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }
    
    @GetMapping(value = "/listbyshop")
    public IceMachineResult<List<DrainRuleNewDTO>> listByShop(@RequestParam("tenantCode") String tenantCode,
                                                              @RequestParam("shopCode") String shopCode) {
        log.debug("listByShop|entering|tenantCode=" +  tenantCode + ";shopCode=" + shopCode);
        IceMachineResult<List<DrainRuleNewDTO>> rtn = service.listByShopCode(tenantCode, shopCode);
        log.debug("listByShop|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<DrainRuleNewDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                             @RequestParam(name = "drainRuleCode", required = false) String drainRuleCode,
                                                             @RequestParam(name = "drainRuleName", required = false) String drainRuleName,
                                                             @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<DrainRuleNewDTO>> rtn = service.search(tenantCode, drainRuleCode, drainRuleName,
                pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody DrainRuleNewPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("drainRuleCode") String drainRuleCode) {
        IceMachineResult<Void> rtn = service.deleteByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public IceMachineResult<Void> putDispatch(@RequestBody DrainRuleDispatchNewPutRequest request) {
        IceMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }
    
    @GetMapping(value = "/dispatch/get")
    public IceMachineResult<DrainRuleDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
                                                                        @RequestParam("drainRuleCode") String drainRuleCode) {
        IceMachineResult<DrainRuleDispatchDTO> rtn = service.getDispatchByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }
}
