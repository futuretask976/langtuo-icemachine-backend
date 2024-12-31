package com.langtuo.teamachine.web.controller.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.device.MachineMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/deviceset/machine")
@Slf4j
public class MachineController {
    @Resource
    private MachineMgtService service;

    @GetMapping(value = "/get")
    public TeaMachineResult<MachineDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("machineCode") String machineCode) {
        TeaMachineResult<MachineDTO> rtn = service.getByMachineCode(tenantCode, machineCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public TeaMachineResult<List<MachineDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        log.debug("list|entering|tenantCode=" +  tenantCode);
        TeaMachineResult<List<MachineDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<MachineDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "machineCode", required = false) String machineCode,
            @RequestParam(name = "screenCode", required = false) String screenCode,
            @RequestParam(name = "elecBoardCode", required = false) String elecBoardCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        log.debug("search|entering|tenantCode=" +  tenantCode + ";machineCode=" + machineCode);
        TeaMachineResult<PageDTO<MachineDTO>> rtn = service.search(tenantCode, machineCode, screenCode, elecBoardCode,
                shopCode, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/activate")
    public TeaMachineResult<MachineDTO> activate(@RequestBody MachineActivatePutRequest request) {
        log.debug("activate|entering|request=" + (request == null ? null : JSON.toJSONString(request)));
        TeaMachineResult<MachineDTO> rtn = service.activate(request);
        log.debug("activate|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @PutMapping(value = "/update")
    public TeaMachineResult<Void> update(@RequestBody MachineUpdatePutRequest request) {
        log.debug("update|entering|request=" + (request == null ? null : JSON.toJSONString(request)));
        TeaMachineResult<Void> rtn = service.put(request);
        log.debug("update|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("machineCode") String machineCode) {
        TeaMachineResult<Void> rtn = service.deleteByMachineCode(tenantCode, machineCode);
        return rtn;
    }
}
