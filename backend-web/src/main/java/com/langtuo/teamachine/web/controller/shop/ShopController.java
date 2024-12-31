package com.langtuo.teamachine.web.controller.shop;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.shop.ShopDTO;
import com.langtuo.teamachine.api.request.shop.ShopPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.shop.ShopMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/shopset/shop")
public class ShopController {
    @Resource
    private ShopMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<ShopDTO> get(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("shopCode") String shopCode) {
        IceMachineResult<ShopDTO> rtn = service.getByShopCode(tenantCode, shopCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<ShopDTO>> list(@RequestParam("tenantCode") String tenantCode,
                                                @RequestParam(name="shopGroupCode", required = false) String shopGroupCode) {
        IceMachineResult<List<ShopDTO>> rtn = service.listByShopGroupCode(tenantCode, shopGroupCode);
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<ShopDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                     @RequestParam(name = "shopName", required = false) String shopName,
                                                     @RequestParam(name = "shopGroupCode", required = false) String shopGroupCode,
                                                     @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<ShopDTO>> rtn = service.search(tenantCode, shopName, shopGroupCode, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody ShopPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("shopCode") String shopCode) {
        IceMachineResult<Void> rtn = service.deleteByShopCode(tenantCode, shopCode);
        return rtn;
    }
}
