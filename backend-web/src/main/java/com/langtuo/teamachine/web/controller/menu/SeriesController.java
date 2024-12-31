package com.langtuo.teamachine.web.controller.menu;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.menu.SeriesDTO;
import com.langtuo.teamachine.api.request.menu.SeriesPutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.menu.SeriesMgtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/menuset/series")
public class SeriesController {
    @Resource
    private SeriesMgtService service;

    @GetMapping(value = "/get")
    public IceMachineResult<SeriesDTO> get(@RequestParam("tenantCode") String tenantCode,
                                           @RequestParam("seriesCode") String seriesCode) {
        IceMachineResult<SeriesDTO> rtn = service.getBySeriesCode(tenantCode, seriesCode);
        return rtn;
    }

    @GetMapping(value = "/list")
    public IceMachineResult<List<SeriesDTO>> list(@RequestParam("tenantCode") String tenantCode) {
        IceMachineResult<List<SeriesDTO>> rtn = service.list(tenantCode);
        return rtn;
    }

    @GetMapping(value = "/search")
    public IceMachineResult<PageDTO<SeriesDTO>> search(@RequestParam("tenantCode") String tenantCode,
                                                       @RequestParam(name = "seriesCode", required = false) String seriesCode,
                                                       @RequestParam(name = "seriesName", required = false) String seriesName,
                                                       @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IceMachineResult<PageDTO<SeriesDTO>> rtn = service.search(tenantCode, seriesCode, seriesName,
                pageNum, pageSize);
        return rtn;
    }

    @PutMapping(value = "/put")
    public IceMachineResult<Void> put(@RequestBody SeriesPutRequest request) {
        IceMachineResult<Void> rtn = service.put(request);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public IceMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
                                         @RequestParam("seriesCode") String seriesCode) {
        IceMachineResult<Void> rtn = service.deleteBySeriesCode(tenantCode, seriesCode);
        return rtn;
    }
}
