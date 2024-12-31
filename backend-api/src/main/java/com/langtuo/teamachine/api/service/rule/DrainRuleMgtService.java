package com.langtuo.teamachine.api.service.rule;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleNewDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.DrainRuleDispatchNewPutRequest;
import com.langtuo.teamachine.api.request.rule.DrainRuleNewPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface DrainRuleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<DrainRuleNewDTO> getByDrainRuleCode(String tenantCode, String drainRuleCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<DrainRuleNewDTO>> search(String tenantCode, String drainRuleCode, String drainRuleName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<DrainRuleNewDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<DrainRuleNewDTO>> listByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(DrainRuleNewPutRequest request);

    /**
     *
     * @param tenantCode
     * @param drainRuleCode
     * @return
     */
    TeaMachineResult<Void> deleteByDrainRuleCode(String tenantCode, String drainRuleCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> putDispatch(DrainRuleDispatchNewPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<DrainRuleDispatchDTO> getDispatchByDrainRuleCode(String tenantCode, String drainRuleCode);
}
