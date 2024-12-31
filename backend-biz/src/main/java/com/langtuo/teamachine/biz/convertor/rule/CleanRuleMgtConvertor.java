package com.langtuo.teamachine.biz.convertor.rule;

import com.langtuo.teamachine.api.model.rule.CleanRuleDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleStepDTO;
import com.langtuo.teamachine.api.request.rule.CleanRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.CleanRulePutRequest;
import com.langtuo.teamachine.dao.accessor.rule.CleanRuleExceptAccessor;
import com.langtuo.teamachine.dao.accessor.rule.CleanRuleStepAccessor;
import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import com.langtuo.teamachine.dao.po.rule.CleanRuleExceptPO;
import com.langtuo.teamachine.dao.po.rule.CleanRulePO;
import com.langtuo.teamachine.dao.po.rule.CleanRuleStepPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CleanRuleMgtConvertor {
    public static List<CleanRuleDTO> convertToCleanRuleDTO(List<CleanRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<CleanRuleDTO> list = poList.stream()
                .map(po -> convertToCleanRuleStepDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static CleanRuleDTO convertToCleanRuleStepDTO(CleanRulePO po) {
        if (po == null) {
            return null;
        }

        CleanRuleDTO dto = new CleanRuleDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setCleanRuleCode(po.getCleanRuleCode());
        dto.setCleanRuleName(po.getCleanRuleName());
        dto.setPermitBatch(po.getPermitBatch());
        dto.setPermitRemind(po.getPermitRemind());

        CleanRuleStepAccessor cleanRuleStepAccessor = SpringAccessorUtils.getCleanRuleStepAccessor();
        List<CleanRuleStepPO> cleanRuleStepPOList = cleanRuleStepAccessor.listByCleanRuleCode(
                po.getTenantCode(), dto.getCleanRuleCode());
        if (!CollectionUtils.isEmpty(cleanRuleStepPOList)) {
            dto.setCleanRuleStepList(convertToCleanRuleStepDTO(cleanRuleStepPOList));
        }

        CleanRuleExceptAccessor cleanRuleExceptAccessor = SpringAccessorUtils.getCleanRuleExceptAccessor();
        List<CleanRuleExceptPO> cleanRuleExceptPOList = cleanRuleExceptAccessor.listByCleanRuleCode(
                po.getTenantCode(), dto.getCleanRuleCode());
        if (!CollectionUtils.isEmpty(cleanRuleExceptPOList)) {
            dto.setExceptToppingCodeList(cleanRuleExceptPOList.stream()
                    .map(cleanRuleExceptPO -> cleanRuleExceptPO.getExceptToppingCode())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static List<CleanRuleStepDTO> convertToCleanRuleStepDTO(List<CleanRuleStepPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<CleanRuleStepDTO> list = poList.stream()
                .map(po -> {
                    CleanRuleStepDTO dto = new CleanRuleStepDTO();
                    dto.setTenantCode(po.getTenantCode());
                    dto.setCleanRuleCode(po.getCleanRuleCode());
                    dto.setCleanContent(po.getCleanContent());
                    dto.setRemindContent(po.getRemindContent());
                    dto.setRemindTitle(po.getRemindTitle());
                    dto.setSoakMin(po.getSoakMin());
                    dto.setStepIndex(po.getStepIndex());
                    dto.setFlushIntervalMin(po.getFlushIntervalMin());
                    dto.setWashSec(po.getWashSec());
                    dto.setFlushSec(po.getFlushSec());
                    dto.setRecycleSec(po.getRecycleSec());
                    dto.setStepIndex(po.getStepIndex());
                    dto.setNeedConfirm(po.getNeedConfirm());
                    dto.setCleanAgentType(po.getCleanAgentType());
                    return dto;
                })
                .collect(Collectors.toList());
        return list;
    }

    public static CleanRulePO convertToCleanRulePO(CleanRulePutRequest request) {
        if (request == null) {
            return null;
        }

        CleanRulePO po = new CleanRulePO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setCleanRuleCode(request.getCleanRuleCode());
        po.setCleanRuleName(request.getCleanRuleName());
        po.setPermitBatch(request.getPermitBatch());
        po.setPermitRemind(request.getPermitRemind());
        return po;
    }

    public static List<CleanRuleStepPO> convertToCleanRuleStepPO(CleanRulePutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getCleanRuleStepList())) {
            return null;
        }

        List<CleanRuleStepPO> list = request.getCleanRuleStepList().stream()
                .filter(Objects::nonNull)
                .map(cleanRuleStepPutRequest -> {
                    CleanRuleStepPO po = new CleanRuleStepPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setCleanRuleCode(request.getCleanRuleCode());
                    po.setCleanContent(cleanRuleStepPutRequest.getCleanContent());
                    po.setRemindContent(cleanRuleStepPutRequest.getRemindContent());
                    po.setRemindTitle(cleanRuleStepPutRequest.getRemindTitle());
                    po.setSoakMin(cleanRuleStepPutRequest.getSoakMin());
                    po.setStepIndex(cleanRuleStepPutRequest.getStepIndex());
                    po.setFlushIntervalMin(cleanRuleStepPutRequest.getFlushIntervalMin());
                    po.setWashSec(cleanRuleStepPutRequest.getWashSec());
                    po.setFlushSec(cleanRuleStepPutRequest.getFlushSec());
                    po.setRecycleSec(cleanRuleStepPutRequest.getRecycleSec());
                    po.setStepIndex(cleanRuleStepPutRequest.getStepIndex());
                    po.setNeedConfirm(cleanRuleStepPutRequest.getNeedConfirm());
                    po.setCleanAgentType(cleanRuleStepPutRequest.getCleanAgentType());
                    return po;
                }).collect(Collectors.toList());
        return list;
    }

    public static List<ConfigRuleDispatchPO> convertToCleanRuleStepDTO(CleanRuleDispatchPutRequest request) {
        String tenantCode = request.getTenantCode();
        String cleanRuleCode = request.getCleanRuleCode();

        return request.getShopGroupCodeList().stream()
                .map(shopGroupCode -> {
                    ConfigRuleDispatchPO po = new ConfigRuleDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setCleanRuleCode(cleanRuleCode);
                    po.setShopGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }

    public static List<CleanRuleExceptPO> convertToCleanRuleExceptPO(CleanRulePutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getExceptToppingCodeList())) {
            return null;
        }

        List<CleanRuleExceptPO> poList = request.getExceptToppingCodeList().stream()
                .map(exceptToppingCode -> {
                    CleanRuleExceptPO po = new CleanRuleExceptPO();
                    po.setExceptToppingCode(exceptToppingCode);
                    po.setTenantCode(request.getTenantCode());
                    po.setCleanRuleCode(request.getCleanRuleCode());
                    return po;
                }).collect(Collectors.toList());
        return poList;
    }
}
