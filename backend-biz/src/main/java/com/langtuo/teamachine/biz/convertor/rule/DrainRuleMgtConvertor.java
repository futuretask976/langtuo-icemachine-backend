package com.langtuo.teamachine.biz.convertor.rule;

import com.langtuo.teamachine.api.model.rule.DrainRuleNewDTO;
import com.langtuo.teamachine.api.request.rule.DrainRuleDispatchNewPutRequest;
import com.langtuo.teamachine.api.request.rule.DrainRuleNewPutRequest;
import com.langtuo.teamachine.dao.accessor.rule.DrainRuleExceptAccessor;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DrainRuleMgtConvertor {
    public static List<DrainRuleNewDTO> convertToDrainRuleDTO(List<DrainRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<DrainRuleNewDTO> list = poList.stream()
                .map(po -> convertToDrainRuleDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static DrainRuleNewDTO convertToDrainRuleDTO(DrainRulePO po) {
        if (po == null) {
            return null;
        }

        DrainRuleNewDTO dto = new DrainRuleNewDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTenantCode(po.getTenantCode());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setDrainRuleCode(po.getDrainRuleCode());
        dto.setDrainRuleName(po.getDrainRuleName());
        dto.setFlushSec(po.getFlushSec());

        DrainRuleExceptAccessor drainRuleExceptAccessor = SpringAccessorUtils.getDrainRuleExceptNewAccessor();
        List<DrainRuleExceptPO> poList = drainRuleExceptAccessor.listByDrainRuleCode(
                po.getTenantCode(), po.getDrainRuleCode());
        if (!CollectionUtils.isEmpty(poList)) {
            dto.setExceptToppingCodeList(poList.stream()
                    .map(DrainRuleExceptPO::getExceptToppingCode)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static DrainRulePO convertToDrainRuleNewPO(DrainRuleNewPutRequest request) {
        if (request == null) {
            return null;
        }

        DrainRulePO po = new DrainRulePO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setDrainRuleCode(request.getDrainRuleCode());
        po.setDrainRuleName(request.getDrainRuleName());
        po.setFlushSec(request.getFlushSec());
        return po;
    }

    public static List<DrainRuleExceptPO> convertToDrainRuleExceptNewPO(DrainRuleNewPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getExceptToppingCodeList())) {
            return null;
        }

        List<DrainRuleExceptPO> list = request.getExceptToppingCodeList().stream()
                .filter(Objects::nonNull)
                .map(toppingCode -> {
                    DrainRuleExceptPO po = new DrainRuleExceptPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setDrainRuleCode(request.getDrainRuleCode());
                    po.setExceptToppingCode(toppingCode);
                    return po;
                }).collect(Collectors.toList());
        return list;
    }

    public static List<DrainRuleDispatchPO> convertToDrainRuleDTO(DrainRuleDispatchNewPutRequest request) {
        String tenantCode = request.getTenantCode();
        String openRuleCode = request.getDrainRuleCode();

        return request.getShopGroupCodeList().stream()
                .map(shopGroupCode -> {
                    DrainRuleDispatchPO po = new DrainRuleDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setDrainRuleCode(openRuleCode);
                    po.setShopGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
