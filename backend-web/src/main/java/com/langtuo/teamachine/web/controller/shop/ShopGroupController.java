package com.langtuo.teamachine.web.controller.shop;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.MachineGroupDTO;
import com.langtuo.teamachine.api.request.device.ShopGroupPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.device.ShopGroupMgtService;
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
    public IceMachineResult<MachineGroupDTO> get(@RequestParam("tenantCode") String tenantCode,
                                                 @RequestParam("shopGroupCode") String shopGroupCode) {
        IceMachineResult<MachineGroupDTO> rtn = service.getByShopGroupCode(tenantCode, shopGroupCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public IceMachineResult<List<MachineGroupDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<MachineGroupDTO>> rtn = service.list(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<MachineGroupDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                             @RequestParam(name = "shopGroupName", required = false) String shopGroupName,
                                                             @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<MachineGroupDTO>> rtn = service.search(tenantCode, shopGroupName, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody ShopGroupPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("shopGroupCode") String shopGroupCode) {
        IceMachineResult<Void> rtn = service.deleteByShopGroupCode(tenantCode, shopGroupCode);
        return rtn;
    }
}
