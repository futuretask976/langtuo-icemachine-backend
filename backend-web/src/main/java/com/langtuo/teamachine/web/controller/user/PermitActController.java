package com.langtuo.teamachine.web.controller.user;

import com.langtuo.teamachine.api.result.IceMachineResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/userset/permitact")
@Slf4j
public class PermitActController {
    @Resource
    private PermitActMgtService service;

    /**
     * url: http://{host}:{port}/teamachinebackend/userset/permitact/list
     * @return
     */
    @GetMapping(value = "/list")
    public IceMachineResult<List<PermitActGroupDTO>> list() {
        IceMachineResult<List<PermitActGroupDTO>> rtn = service.listPermitActGroup();
        return rtn;
    }
}
