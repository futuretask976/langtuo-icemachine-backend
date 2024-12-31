package com.langtuo.teamachine.web.controller.shop;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.shop.ShopGroupDTO;
import com.langtuo.teamachine.api.request.shop.ShopGroupPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.shop.ShopGroupMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/shopset/group")
public class ShopGroupController {
    @Resource
    private ShopGroupMgtService service;
    
    @GetMapping(value = "/get")
    public TeaMachineResult<ShopGroupDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopGroupCode") String shopGroupCode) {
        TeaMachineResult<ShopGroupDTO> rtn = service.getByShopGroupCode(tenantCode, shopGroupCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public TeaMachineResult<List<ShopGroupDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<ShopGroupDTO>> rtn = service.list(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<ShopGroupDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "shopGroupName", required = false) String shopGroupName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<ShopGroupDTO>> rtn = service.search(tenantCode, shopGroupName, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody ShopGroupPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopGroupCode") String shopGroupCode) {
        TeaMachineResult<Void> rtn = service.deleteByShopGroupCode(tenantCode, shopGroupCode);
        return rtn;
    }
}
