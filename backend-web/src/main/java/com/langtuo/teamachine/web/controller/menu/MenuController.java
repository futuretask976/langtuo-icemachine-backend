package com.langtuo.teamachine.web.controller.menu;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.menu.MenuDTO;
import com.langtuo.teamachine.api.model.menu.MenuDispatchDTO;
import com.langtuo.teamachine.api.request.menu.MenuDispatchPutRequest;
import com.langtuo.teamachine.api.request.menu.MenuPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
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
    public TeaMachineResult<MenuDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("menuCode") String menuCode) {
        TeaMachineResult<MenuDTO> rtn = service.getByMenuCode(tenantCode, menuCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public TeaMachineResult<List<MenuDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        TeaMachineResult<List<MenuDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/trigger")
    public TeaMachineResult<Void> trigger(@RequestParam("tenantCode") String tenantCode
            , @RequestParam("shopGroupCode") String shopGroupCode, @RequestParam("machineCode") String machineCode) {
        log.debug("trigger|entering|tenantCode=" +  tenantCode + ";shopGroupCode=" + shopGroupCode + ";machineCode=" + machineCode);
        TeaMachineResult<Void> rtn = service.triggerDispatchByShopGroupCode(tenantCode, shopGroupCode, machineCode);
        log.debug("trigger|exiting|rtn=" +  (rtn == null ? null : JSON.toJSONString(rtn)));
        return rtn;
    }

    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<MenuDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuName", required = false) String menuName,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<MenuDTO>> rtn = service.search(tenantCode, menuCode, menuName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public TeaMachineResult<Void> put(@RequestBody MenuPutRequest request) {
        TeaMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("menuCode") String menuCode) {
        TeaMachineResult<Void> rtn = service.deleteByMenuCode(tenantCode, menuCode);
        return rtn;
    }

    @PutMapping(value = "/dispatch/put")
    public TeaMachineResult<Void> putDispatch(@RequestBody MenuDispatchPutRequest request) {
        TeaMachineResult<Void> rtn = service.putDispatch(request);
        return rtn;
    }

    @GetMapping(value = "/dispatch/get")
    public TeaMachineResult<MenuDispatchDTO> getDispatchByMenuCode(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("menuCode") String menuCode) {
        TeaMachineResult<MenuDispatchDTO> rtn = service.getDispatchByMenuCode(tenantCode, menuCode);
        return rtn;
    }
}
