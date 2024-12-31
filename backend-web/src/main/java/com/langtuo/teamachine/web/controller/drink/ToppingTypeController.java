package com.langtuo.teamachine.web.controller.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingTypeDTO;
import com.langtuo.teamachine.api.request.drink.ToppingTypePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.drink.ToppingTypeMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/drinkset/topping/type")
public class ToppingTypeController {
    @Resource
    private ToppingTypeMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<ToppingTypeDTO> get(@RequestParam("tenantCode") String tenantCode,
                                                @RequestParam("toppingTypeCode") String toppingTypeCode) {
        IceMachineResult<ToppingTypeDTO> rtn = service.getByToppingTypeCode(tenantCode, toppingTypeCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<ToppingTypeDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<ToppingTypeDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ToppingTypeDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                            @RequestParam(name = "toppingTypeCode", required = false) String toppingTypeCode,
                                                            @RequestParam(name = "toppingTypeName", required = false) String toppingTypeName,
                                                            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ToppingTypeDTO>> rtn = service.search(tenantCode, toppingTypeCode, toppingTypeName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody ToppingTypePutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("toppingTypeCode") String toppingTypeCode) {
        IceMachineResult<Void> rtn = service.deleteByToppingTypeCode(tenantCode, toppingTypeCode);
        return rtn;
    }
}
