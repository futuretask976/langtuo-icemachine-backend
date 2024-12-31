package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.model.user.OrgDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.OrgPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.user.OrgMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/userset/org")
public class OrgController {
    @Resource
    private OrgMgtService service;
    
    @GetMapping(value = "/get")
    public TeaMachineResult<OrgDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("orgName") String orgName) {
        TeaMachineResult<OrgDTO> rtn = service.getByOrgName(tenantCode, orgName);
        return rtn;
    }
    
    @GetMapping(value = "/listbydepth")
    public TeaMachineResult<OrgDTO> listByDepth(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<OrgDTO> rtn = service.getTop(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public TeaMachineResult<List<OrgDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<OrgDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<OrgDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "orgName", required = false) String orgName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrgDTO>> rtn = service.search(tenantCode, orgName, pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody OrgPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("orgName") String orgName) {
        TeaMachineResult<Void> rtn = service.deleteByOrgName(tenantCode, orgName);
        return rtn;
    }
}
