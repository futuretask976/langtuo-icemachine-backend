package com.langtuo.teamachine.biz.convertor.rule;

import com.langtuo.teamachine.api.model.rule.ConfigRuleDTO;
import com.langtuo.teamachine.api.request.rule.ConfigRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.ConfigRulePutRequest;
import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import com.langtuo.teamachine.dao.po.rule.ConfigRulePO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigRuleMgtConvertor {
    public static List<ConfigRuleDTO> convertToCleanRuleDTO(List<ConfigRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<ConfigRuleDTO> list = poList.stream()
                .map(po -> convertToCleanRuleStepDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static ConfigRuleDTO convertToCleanRuleStepDTO(ConfigRulePO po) {
        if (po == null) {
            return null;
        }

        ConfigRuleDTO dto = new ConfigRuleDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setConfigRuleCode(po.getConfigRuleCode());
        dto.setConfigRuleName(po.getConfigRuleName());
        return dto;
    }

    public static ConfigRulePO convertToCleanRulePO(ConfigRulePutRequest request) {
        if (request == null) {
            return null;
        }

        ConfigRulePO po = new ConfigRulePO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setConfigRuleCode(request.getConfigRuleCode());
        po.setConfigRuleName(request.getConfigRuleName());
        return po;
    }

    public static List<ConfigRuleDispatchPO> convertToCleanRuleStepDTO(ConfigRuleDispatchPutRequest request) {
        String tenantCode = request.getTenantCode();
        String cleanRuleCode = request.getConfigRuleCode();

        return request.getMachineGroupCodeList().stream()
                .map(shopGroupCode -> {
                    ConfigRuleDispatchPO po = new ConfigRuleDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setConfigRuleCode(cleanRuleCode);
                    po.setMachineGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
