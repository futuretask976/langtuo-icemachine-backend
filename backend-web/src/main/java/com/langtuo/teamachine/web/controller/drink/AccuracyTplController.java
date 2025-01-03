package com.langtuo.teamachine.web.controller.drink;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.AccuracyTplDTO;
import com.langtuo.teamachine.api.request.drink.AccuracyTplPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.drink.AccuracyTplMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/accuracy")
@Slf4j
public class AccuracyTplController {
    @Resource
    private AccuracyTplMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<AccuracyTplDTO> get(@RequestParam("tenantCode") String tenantCode,
                                                @RequestParam("templateCode") String templateCode) {
        IceMachineResult<AccuracyTplDTO> rtn = service.getByTplCode(tenantCode, templateCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<AccuracyTplDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        log.debug("list|entering|tenantCode=" +  tenantCode);
        IceMachineResult<List<AccuracyTplDTO>> rtn = service.list(tenantCode);
        log.debug("list|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<AccuracyTplDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                            @RequestParam(name = "templateCode", required = false) String templateCode,
                                                            @RequestParam(name = "templateName", required = false) String templateName,
                                                            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<AccuracyTplDTO>> rtn = service.search(tenantCode, templateCode, templateName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody AccuracyTplPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("templateCode") String templateCode) {
        IceMachineResult<Void> rtn = service.deleteByTplCode(tenantCode, templateCode);
        return rtn;
    }
}
