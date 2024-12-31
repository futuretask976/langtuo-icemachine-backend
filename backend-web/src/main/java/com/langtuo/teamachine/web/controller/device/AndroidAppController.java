package com.langtuo.teamachine.web.controller.device;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDTO;
import com.langtuo.teamachine.api.model.device.AndroidAppDispatchDTO;
import com.langtuo.teamachine.api.request.device.AndroidAppDispatchPutRequest;
import com.langtuo.teamachine.api.request.device.AndroidAppPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.device.AndroidAppMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/deviceset/android/app")
@Slf4j
public class AndroidAppController {
    @Resource
    private AndroidAppMgtService service;

    @GetMapping(value = "/list")
    public TeaMachineResult<List<AndroidAppDTO>> list(@RequestParam("limit") int limit) {
        TeaMachineResult<List<AndroidAppDTO>> rtn = service.listByLimit(limit);
        return rtn;
    }

    @GetMapping(value = "/get")
    public TeaMachineResult<AndroidAppDTO> get(@RequestParam("version") String version) {
        TeaMachineResult<AndroidAppDTO> rtn = service.getByVersion(version);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<AndroidAppDTO>> search(
            @RequestParam(name = "version", required = false) String version,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<AndroidAppDTO>> rtn = service.search(version, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody AndroidAppPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("version") String version) {
        TeaMachineResult<Void> rtn = service.delete(tenantCode, version);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public TeaMachineResult<Void> putDispatch(@RequestBody AndroidAppDispatchPutRequest request) {
        TeaMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }

    @GetMapping(value = "/dispatch/get")
    public TeaMachineResult<AndroidAppDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("version") String version) {
        TeaMachineResult<AndroidAppDispatchDTO> rtn = service.getDispatchByVersion(tenantCode, version);
        return rtn;
    }
}
