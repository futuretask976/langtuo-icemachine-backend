package com.langtuo.teamachine.web.controller.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingTypeDTO;
import com.langtuo.teamachine.api.request.drink.ToppingTypePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
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
    public TeaMachineResult<ToppingTypeDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("toppingTypeCode") String toppingTypeCode) {
        TeaMachineResult<ToppingTypeDTO> rtn = service.getByToppingTypeCode(tenantCode, toppingTypeCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public TeaMachineResult<List<ToppingTypeDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<ToppingTypeDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<ToppingTypeDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "toppingTypeCode", required = false) String toppingTypeCode,
            @RequestParam(name = "toppingTypeName", required = false) String toppingTypeName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<ToppingTypeDTO>> rtn = service.search(tenantCode, toppingTypeCode, toppingTypeName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody ToppingTypePutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("toppingTypeCode") String toppingTypeCode) {
        TeaMachineResult<Void> rtn = service.deleteByToppingTypeCode(tenantCode, toppingTypeCode);
        return rtn;
    }
}
