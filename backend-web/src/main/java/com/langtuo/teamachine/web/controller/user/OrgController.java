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
@RequestMapping("/userset/org")
public class OrgController {
    @Resource
    private OrgMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<OrgDTO> get(@RequestParam("tenantCode") String tenantCode,
                                        @RequestParam("orgName") String orgName) {
        IceMachineResult<OrgDTO> rtn = service.getByOrgName(tenantCode, orgName);
        return rtn;
    }
    
    @GetMapping(value = "/listbydepth")
    public IceMachineResult<OrgDTO> listByDepth(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<OrgDTO> rtn = service.getTop(tenantCode);
        return rtn;
    }
    
    @GetMapping(value = "/list")
    public IceMachineResult<List<OrgDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<OrgDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<OrgDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                    @RequestParam(name = "orgName", required = false) String orgName,
                                                    @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<OrgDTO>> rtn = service.search(tenantCode, orgName, pageNum, pageSize);
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody OrgPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("orgName") String orgName) {
        IceMachineResult<Void> rtn = service.deleteByOrgName(tenantCode, orgName);
        return rtn;
    }
}
