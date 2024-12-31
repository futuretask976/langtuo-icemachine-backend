package com.langtuo.teamachine.web.controller.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.WarningRuleDTO;
import com.langtuo.teamachine.api.model.rule.WarningRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.WarningRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.WarningRulePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.rule.WarningRuleMgtService;
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
    public TeaMachineResult<WarningRuleDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("warningRuleCode") String warningRuleCode) {
        TeaMachineResult<WarningRuleDTO> rtn = service.getByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }

    @GetMapping(value = "/listbyshop")
    public TeaMachineResult<List<WarningRuleDTO>> listByShop(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopCode") String shopCode) {
        log.debug("listByShop|entering|tenantCode=" +  tenantCode + ";shopCode=" + shopCode);
        TeaMachineResult<List<WarningRuleDTO>> rtn = service.listByShopCode(tenantCode, shopCode);
        log.debug("listByShop|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<WarningRuleDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "warningRuleCode", required = false) String warningRuleCode,
            @RequestParam(name = "warningRuleName", required = false) String warningRuleName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<WarningRuleDTO>> rtn = service.search(tenantCode, warningRuleCode, warningRuleName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody WarningRulePutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("warningRuleCode") String warningRuleCode) {
        TeaMachineResult<Void> rtn = service.deleteByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public TeaMachineResult<Void> putDispatch(@RequestBody WarningRuleDispatchPutRequest request) {
        TeaMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }

    @GetMapping(value = "/dispatch/get")
    public TeaMachineResult<WarningRuleDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("warningRuleCode") String warningRuleCode) {
        TeaMachineResult<WarningRuleDispatchDTO> rtn = service.getDispatchByWarningRuleCode(tenantCode, warningRuleCode);
        return rtn;
    }
}
