package com.langtuo.teamachine.api.service.rule;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.CleanRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.CleanRulePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface CleanRuleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<CleanRuleDTO> getByCleanRuleCode(String tenantCode, String cleanRuleCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<CleanRuleDTO>> search(String tenantCode, String cleanRuleCode, String cleanRuleName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<CleanRuleDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<CleanRuleDTO>> listByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(CleanRulePutRequest request);

    /**
     *
     * @param tenantCode
     * @param cleanRuleCode
     * @return
     */
    TeaMachineResult<Void> deleteByCleanRuleCode(String tenantCode, String cleanRuleCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> putDispatch(CleanRuleDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<CleanRuleDispatchDTO> getDispatchByCleanRuleCode(String tenantCode, String cleanRuleCode);
}
