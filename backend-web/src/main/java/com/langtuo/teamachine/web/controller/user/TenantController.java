package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
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
    public TeaMachineResult<TenantDTO> get(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<TenantDTO> rtn = service.get(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public TeaMachineResult<List<TenantDTO>> list() {
        TeaMachineResult<List<TenantDTO>> rtn = service.list();
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<TenantDTO>> search(
            @RequestParam(name = "tenantName", required = false) String tenantName,
            @RequestParam(name = "contactPerson", required = false) String contactPerson,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<TenantDTO>> rtn = service.search(tenantName, contactPerson, pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody TenantPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<Void> rtn = service.delete(tenantCode);
        return rtn;
    }
}
