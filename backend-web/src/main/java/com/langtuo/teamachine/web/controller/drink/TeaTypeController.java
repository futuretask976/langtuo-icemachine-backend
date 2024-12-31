package com.langtuo.teamachine.web.controller.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.TeaTypeDTO;
import com.langtuo.teamachine.api.request.drink.TeaTypePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.TeaTypeMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/tea/type")
public class TeaTypeController {
    @Resource
    private TeaTypeMgtService service;

    @GetMapping(value = "/get")
    public TeaMachineResult<TeaTypeDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("teaTypeCode") String teaTypeCode) {
        TeaMachineResult<TeaTypeDTO> rtn = service.getByTeaTypeCode(tenantCode, teaTypeCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public TeaMachineResult<List<TeaTypeDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<TeaTypeDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<TeaTypeDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "teaTypeCode", required = false) String teaTypeCode,
            @RequestParam(name = "teaTypeName", required = false) String teaTypeName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<TeaTypeDTO>> rtn = service.search(tenantCode, teaTypeCode, teaTypeName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody TeaTypePutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("teaTypeCode") String teaTypeCode) {
        TeaMachineResult<Void> rtn = service.deleteByTeaTypeCode(tenantCode, teaTypeCode);
        return rtn;
    }
}
