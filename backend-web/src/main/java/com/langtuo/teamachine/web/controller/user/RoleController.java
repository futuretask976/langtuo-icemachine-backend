package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.model.user.RoleDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.RolePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.user.RoleMgtService;
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
    public TeaMachineResult<RoleDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("roleCode") String roleCode) {
        TeaMachineResult<RoleDTO> rtn = service.getByCode(tenantCode, roleCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public TeaMachineResult<List<RoleDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<RoleDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<RoleDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "roleName", required = false) String roleName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<RoleDTO>> rtn = service.search(tenantCode, roleName, pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody RolePutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("roleCode") String roleCode) {
        TeaMachineResult<Void> rtn = service.delete(tenantCode, roleCode);
        return rtn;
    }
}
