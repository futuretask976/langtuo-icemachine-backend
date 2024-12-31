package com.langtuo.teamachine.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.request.user.AdminPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.user.AdminMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/userset/admin")
@Slf4j
public class AdminController {
    @Resource
    private AdminMgtService service;
    
    @GetMapping(value = "/get")
    public IceMachineResult<AdminDTO> get(@RequestParam("tenantCode") String tenantCode,
                                          @RequestParam("loginName") String loginName) {
        log.debug("adminController|get|entering|" + tenantCode + "|" + loginName + "|001");
        IceMachineResult<AdminDTO> rtn = service.getByLoginName(tenantCode, loginName);
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<AdminDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                      @RequestParam(name = "loginName", required = false) String loginName,
                                                      @RequestParam(name = "roleCode", required = false) String roleCode,
                                                      @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        log.debug("search|entering|tenantCode=" + tenantCode + ";loginName=" + loginName);
        IceMachineResult<PageDTO<AdminDTO>> rtn = service.search(tenantCode, loginName, roleCode, pageNum, pageSize);
        log.debug("search|exiting|rtn=" + (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }
    
    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody AdminPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }
    
    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("loginName") String loginName) {
        IceMachineResult<Void> rtn = service.deleteByLoginName(tenantCode, loginName);
        return rtn;
    }
}
