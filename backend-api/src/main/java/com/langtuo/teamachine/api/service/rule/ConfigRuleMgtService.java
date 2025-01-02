package com.langtuo.teamachine.api.service.rule;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.ConfigRuleDTO;
import com.langtuo.teamachine.api.model.rule.ConfigRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.ConfigRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.ConfigRulePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;

import java.util.List;

public interface ConfigRuleMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<ConfigRuleDTO> getByCleanRuleCode(String tenantCode, String cleanRuleCode);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<ConfigRuleDTO>> search(String tenantCode, String cleanRuleCode, String cleanRuleName,
                                                    int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<ConfigRuleDTO>> list(String tenantCode);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<List<ConfigRuleDTO>> listByMachineGroupCode(String tenantCode, String shopCode);

    /**
     *
     * @param request
     * @return
     */
    IceMachineResult<Void> put(ConfigRulePutRequest request);

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
    IceMachineResult<Void> putDispatch(ConfigRuleDispatchPutRequest request);

    /**
     *
     * @param tenantCode
     * @return
     */
    IceMachineResult<ConfigRuleDispatchDTO> getDispatchByCleanRuleCode(String tenantCode, String cleanRuleCode);
}
