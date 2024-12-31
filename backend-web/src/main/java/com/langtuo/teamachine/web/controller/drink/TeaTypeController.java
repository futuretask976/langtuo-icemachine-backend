package com.langtuo.teamachine.web.controller.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.TeaTypeDTO;
import com.langtuo.teamachine.api.request.drink.TeaTypePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
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
    public IceMachineResult<TeaTypeDTO> get(@RequestParam("tenantCode") String tenantCode,
                                            @RequestParam("teaTypeCode") String teaTypeCode) {
        IceMachineResult<TeaTypeDTO> rtn = service.getByTeaTypeCode(tenantCode, teaTypeCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<TeaTypeDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<TeaTypeDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<TeaTypeDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                        @RequestParam(name = "teaTypeCode", required = false) String teaTypeCode,
                                                        @RequestParam(name = "teaTypeName", required = false) String teaTypeName,
                                                        @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<TeaTypeDTO>> rtn = service.search(tenantCode, teaTypeCode, teaTypeName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody TeaTypePutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("teaTypeCode") String teaTypeCode) {
        IceMachineResult<Void> rtn = service.deleteByTeaTypeCode(tenantCode, teaTypeCode);
        return rtn;
    }
}
