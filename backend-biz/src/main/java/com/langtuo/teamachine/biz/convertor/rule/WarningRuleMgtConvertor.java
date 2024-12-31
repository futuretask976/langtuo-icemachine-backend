package com.langtuo.teamachine.biz.convertor.rule;

import com.langtuo.teamachine.api.model.rule.WarningRuleDTO;
import com.langtuo.teamachine.api.request.rule.WarningRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.WarningRulePutRequest;
import com.langtuo.teamachine.dao.po.rule.WarningRuleDispatchPO;
import com.langtuo.teamachine.dao.po.rule.WarningRulePO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class WarningRuleMgtConvertor {
    public static List<WarningRuleDTO> convertToWarningRuleDTO(List<WarningRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<WarningRuleDTO> list = poList.stream()
                .map(po -> convertToWarningRuleDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static WarningRuleDTO convertToWarningRuleDTO(WarningRulePO po) {
        if (po == null) {
            return null;
        }

        WarningRuleDTO dto = new WarningRuleDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTenantCode(po.getTenantCode());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setWarningRuleCode(po.getWarningRuleCode());
        dto.setWarningRuleName(po.getWarningRuleName());
        dto.setWarningContent(po.getWarningContent());
        dto.setWarningType(po.getWarningType());
        dto.setThreshold(po.getThreshold());
        dto.setThresholdMode(po.getThresholdMode());
        dto.setComment(po.getComment());
        return dto;
    }

    public static WarningRulePO convertToWarningRuleDTO(WarningRulePutRequest request) {
        if (request == null) {
            return null;
        }

        WarningRulePO po = new WarningRulePO();
        po.setTenantCode(request.getTenantCode());
        po.setComment(request.getComment());
        po.setExtraInfo(request.getExtraInfo());
        po.setWarningRuleCode(request.getWarningRuleCode());
        po.setWarningRuleName(request.getWarningRuleName());
        po.setWarningContent(request.getWarningContent());
        po.setWarningType(request.getWarningType());
        po.setThreshold(request.getThreshold());
        po.setThresholdMode(request.getThresholdMode());
        po.setComment(request.getComment());
        return po;
    }

    public static List<WarningRuleDispatchPO> convertToWarningRuleDTO(WarningRuleDispatchPutRequest request) {
        if (request == null) {
            return null;
        }

        String tenantCode = request.getTenantCode();
        String warningRuleCode = request.getWarningRuleCode();

        return request.getShopGroupCodeList().stream()
                .map(shopGroupCode -> {
                    WarningRuleDispatchPO po = new WarningRuleDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setWarningRuleCode(warningRuleCode);
                    po.setShopGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
