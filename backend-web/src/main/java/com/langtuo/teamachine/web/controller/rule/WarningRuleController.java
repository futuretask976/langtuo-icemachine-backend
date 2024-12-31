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
@RequestMapping("/ruleset/warning")
@Slf4j
public class WarningRuleController {
    @Resource
    private WarningRuleMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<WarningRuleDTO> get(@RequestParam("tenantCode") String tenantCode,
                                                @RequestParam("warningRuleCode") String warningRuleCode) {
        IceMachineResult<WarningRuleDTO> rtn = service.getByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }

    @GetMapping(value = "/listbyshop")
    public IceMachineResult<List<WarningRuleDTO>> listByShop(@RequestParam("tenantCode") String tenantCode,
                                                             @RequestParam("shopCode") String shopCode) {
        log.debug("listByShop|entering|tenantCode=" +  tenantCode + ";shopCode=" + shopCode);
        IceMachineResult<List<WarningRuleDTO>> rtn = service.listByShopCode(tenantCode, shopCode);
        log.debug("listByShop|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<WarningRuleDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                            @RequestParam(name = "warningRuleCode", required = false) String warningRuleCode,
                                                            @RequestParam(name = "warningRuleName", required = false) String warningRuleName,
                                                            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<WarningRuleDTO>> rtn = service.search(tenantCode, warningRuleCode, warningRuleName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody WarningRulePutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("warningRuleCode") String warningRuleCode) {
        IceMachineResult<Void> rtn = service.deleteByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public IceMachineResult<Void> putDispatch(@RequestBody WarningRuleDispatchPutRequest request) {
        IceMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }

    @GetMapping(value = "/dispatch/get")
    public IceMachineResult<WarningRuleDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
                                                                          @RequestParam("warningRuleCode") String warningRuleCode) {
        IceMachineResult<WarningRuleDispatchDTO> rtn = service.getDispatchByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }
}
