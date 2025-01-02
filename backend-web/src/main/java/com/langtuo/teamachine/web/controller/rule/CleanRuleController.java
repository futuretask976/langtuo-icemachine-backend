package com.langtuo.teamachine.web.controller.rule;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.ConfigRuleDTO;
import com.langtuo.teamachine.api.model.rule.ConfigRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.CleanRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.CleanRulePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.rule.ConfigRuleMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/ruleset/clean")
@Slf4j
public class CleanRuleController {
    @Resource
    private ConfigRuleMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<ConfigRuleDTO> get(@RequestParam("tenantCode") String tenantCode,
                                               @RequestParam("cleanRuleCode") String cleanRuleCode) {
        IceMachineResult<ConfigRuleDTO> rtn = service.getByCleanRuleCode(tenantCode, cleanRuleCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public IceMachineResult<List<ConfigRuleDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<ConfigRuleDTO>> rtn = service.list(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/listbyshop")
    public IceMachineResult<List<ConfigRuleDTO>> listByShop(@RequestParam("tenantCode") String tenantCode,
                                                            @RequestParam("shopCode") String shopCode) {
        log.debug("listByShop|entering|tenantCode=" +  tenantCode + ";shopCode=" + shopCode);
        IceMachineResult<List<ConfigRuleDTO>> rtn = service.listByShopCode(tenantCode, shopCode);
        log.debug("listByShop|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ConfigRuleDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                           @RequestParam(name = "cleanRuleCode", required = false) String cleanRuleCode,
                                                           @RequestParam(name = "cleanRuleName", required = false) String cleanRuleName,
                                                           @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ConfigRuleDTO>> rtn = service.search(tenantCode, cleanRuleCode, cleanRuleName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody CleanRulePutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("cleanRuleCode") String cleanRuleCode) {
        IceMachineResult<Void> rtn = service.deleteByCleanRuleCode(tenantCode, cleanRuleCode);
        return rtn;
    }
    
    @PutMapping(value = "/dispatch/put")
    public IceMachineResult<Void> putDispatch(@RequestBody CleanRuleDispatchPutRequest request) {
        IceMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }
    
    @GetMapping(value = "/dispatch/get")
    public IceMachineResult<ConfigRuleDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
                                                                         @RequestParam("cleanRuleCode") String cleanRuleCode) {
        IceMachineResult<ConfigRuleDispatchDTO> rtn = service.getDispatchByCleanRuleCode(tenantCode, cleanRuleCode);
        return rtn;
    }
}
