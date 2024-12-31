package com.langtuo.teamachine.api.service.rule;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.WarningRuleDTO;
import com.langtuo.teamachine.api.model.rule.WarningRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.WarningRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.WarningRulePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface WarningRuleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<WarningRuleDTO> getByWarningRuleCode(String tenantCode, String warningRuleCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<WarningRuleDTO>> search(String tenantCode, String warningRuleCode, String warningRuleName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<WarningRuleDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<WarningRuleDTO>> listByShopCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(WarningRulePutRequest request);

    /**
     *
     * @param tenantCode
     * @param warningRuleCode
     * @return
     */
    TeaMachineResult<Void> deleteByWarningRuleCode(String tenantCode, String warningRuleCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> putDispatch(WarningRuleDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<WarningRuleDispatchDTO> getDispatchByWarningRuleCode(String tenantCode, String warningRuleCode);
}
