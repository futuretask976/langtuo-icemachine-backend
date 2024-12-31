package com.langtuo.teamachine.web.controller.rule;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleNewDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.DrainRuleDispatchNewPutRequest;
import com.langtuo.teamachine.api.request.rule.DrainRuleNewPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.rule.DrainRuleMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/ruleset/drainnew")
@Slf4j
public class DrainRuleController {
    @Resource
    private DrainRuleMgtService service;
    
    @GetMapping(value = "/get")
    public TeaMachineResult<DrainRuleNewDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("drainRuleCode") String drainRuleCode) {
        TeaMachineResult<DrainRuleNewDTO> rtn = service.getByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }
    
    @GetMapping(value = "/listbyshop")
    public TeaMachineResult<List<DrainRuleNewDTO>> listByShop(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopCode") String shopCode) {
        log.debug("listByShop|entering|tenantCode=" +  tenantCode + ";shopCode=" + shopCode);
        TeaMachineResult<List<DrainRuleNewDTO>> rtn = service.listByShopCode(tenantCode, shopCode);
        log.debug("listByShop|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<DrainRuleNewDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "drainRuleCode", required = false) String drainRuleCode,
            @RequestParam(name = "drainRuleName", required = false) String drainRuleName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<DrainRuleNewDTO>> rtn = service.search(tenantCode, drainRuleCode, drainRuleName,
                pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody DrainRuleNewPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("drainRuleCode") String drainRuleCode) {
        TeaMachineResult<Void> rtn = service.deleteByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public TeaMachineResult<Void> putDispatch(@RequestBody DrainRuleDispatchNewPutRequest request) {
        TeaMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }
    
    @GetMapping(value = "/dispatch/get")
    public TeaMachineResult<DrainRuleDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("drainRuleCode") String drainRuleCode) {
        TeaMachineResult<DrainRuleDispatchDTO> rtn = service.getDispatchByDrainRuleCode(tenantCode, drainRuleCode);
        return rtn;
    }
}
