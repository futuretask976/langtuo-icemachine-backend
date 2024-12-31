package com.langtuo.teamachine.web.controller.device;

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
@RequestMapping("/deviceset/model")
@Slf4j
public class ModelController {
    @Resource
    private ModelMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<ModelDTO> get(@RequestParam("modelCode") String modelCode) {
        log.debug("get|entering|modelCode=" +  modelCode);
        IceMachineResult<ModelDTO> rtn = service.getByModelCode(modelCode);
        log.debug("get|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<ModelDTO>> list() {
        IceMachineResult<List<ModelDTO>> rtn = service.list();
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ModelDTO>> search(
            @RequestParam(name = "modelCode", required = false) String modelCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ModelDTO>> rtn = service.search(modelCode, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody ModelPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("modelCode") String modelCode) {
        IceMachineResult<Void> rtn = service.deleteByModelCode(modelCode);
        return rtn;
    }
}
