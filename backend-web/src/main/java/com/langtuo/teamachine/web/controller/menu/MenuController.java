package com.langtuo.teamachine.web.controller.menu;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.menu.MenuDTO;
import com.langtuo.teamachine.api.model.menu.MenuDispatchDTO;
import com.langtuo.teamachine.api.request.menu.MenuDispatchPutRequest;
import com.langtuo.teamachine.api.request.menu.MenuPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.menu.MenuMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/menuset/menu")
@Slf4j
public class MenuController {
    @Resource
    private MenuMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<MenuDTO> get(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("menuCode") String menuCode) {
        IceMachineResult<MenuDTO> rtn = service.getByMenuCode(tenantCode, menuCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<MenuDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<MenuDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/trigger")
    public IceMachineResult<Void> trigger(@RequestParam("tenantCode") String tenantCode
            , @RequestParam("shopGroupCode") String shopGroupCode, @RequestParam("machineCode") String machineCode) {
        log.debug("trigger|entering|tenantCode=" +  tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
        IceMachineResult<Void> rtn = service.triggerDispatchByShopGroupCode(tenantCode, shopGroupCode, machineCode);
        log.debug("trigger|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<MenuDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                     @RequestParam(name = "menuCode", required = false) String menuCode,
                                                     @RequestParam(name = "menuName", required = false) String menuName,
                                                     @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<MenuDTO>> rtn = service.search(tenantCode, menuCode, menuName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody MenuPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("menuCode") String menuCode) {
        IceMachineResult<Void> rtn = service.deleteByMenuCode(tenantCode, menuCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public IceMachineResult<Void> putDispatch(@RequestBody MenuDispatchPutRequest request) {
        IceMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }

    @GetMapping(value = "/dispatch/get")
    public IceMachineResult<MenuDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
                                                                   @RequestParam("menuCode") String menuCode) {
        IceMachineResult<MenuDispatchDTO> rtn = service.getDispatchByMenuCode(tenantCode, menuCode);
        return rtn;
    }
}
