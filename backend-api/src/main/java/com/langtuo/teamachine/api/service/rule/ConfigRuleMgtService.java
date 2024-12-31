package com.langtuo.teamachine.api.service.rule;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.CleanRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.CleanRulePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface ConfigRuleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<CleanRuleDTO> getByCleanRuleCode(String tenantCode, String cleanRuleCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<CleanRuleDTO>> search(String tenantCode, String cleanRuleCode, String cleanRuleName,
                                                   int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<CleanRuleDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<CleanRuleDTO>> listByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(CleanRulePutRequest request);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    IceMachineResult<Void> deleteByCleanRuleCode(String tenantCode, String cleanRuleCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> putDispatch(CleanRuleDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<CleanRuleDispatchDTO> getDispatchByCleanRuleCode(String tenantCode, String cleanRuleCode);
}
