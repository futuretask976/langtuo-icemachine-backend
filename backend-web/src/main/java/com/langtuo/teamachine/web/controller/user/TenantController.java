package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.user.TenantMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/userset/tenant")
@Slf4j
public class TenantController {
    @Resource
    private TenantMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<TenantDTO> get(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<TenantDTO> rtn = service.get(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public IceMachineResult<List<TenantDTO>> list() {
        IceMachineResult<List<TenantDTO>> rtn = service.list();
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<TenantDTO>> search(
            @RequestParam(name = "tenantName", required = false) String tenantName,
            @RequestParam(name = "contactPerson", required = false) String contactPerson,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<TenantDTO>> rtn = service.search(tenantName, contactPerson, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody TenantPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<Void> rtn = service.delete(tenantCode);
        return rtn;
    }
}
