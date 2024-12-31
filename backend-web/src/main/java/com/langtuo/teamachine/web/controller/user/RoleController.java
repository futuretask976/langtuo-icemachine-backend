package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/userset/role")
public class RoleController {
    @Resource
    private RoleMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<RoleDTO> get(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("roleCode") String roleCode) {
        IceMachineResult<RoleDTO> rtn = service.getByCode(tenantCode, roleCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public IceMachineResult<List<RoleDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<RoleDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<RoleDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                     @RequestParam(name = "roleName", required = false) String roleName,
                                                     @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<RoleDTO>> rtn = service.search(tenantCode, roleName, pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody RolePutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("roleCode") String roleCode) {
        IceMachineResult<Void> rtn = service.delete(tenantCode, roleCode);
        return rtn;
    }
}
