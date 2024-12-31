package com.langtuo.teamachine.biz.convertor.drink;

import com.langtuo.teamachine.api.model.drink.AccuracyTplDTO;
import com.langtuo.teamachine.api.request.drink.AccuracyTplPutRequest;
import com.langtuo.teamachine.dao.accessor.drink.AccuracyTplToppingAccessor;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplPO;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplToppingPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AccuracyTplMgtConvertor {
    public static List<AccuracyTplDTO> convertToAccuracyTplPO(List<AccuracyTplPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToAccuracyTplPO(po))
                .collect(Collectors.toList());
    }

    public static AccuracyTplDTO convertToAccuracyTplPO(AccuracyTplPO po) {
        if (po == null) {
            return null;
        }

        AccuracyTplDTO dto = new AccuracyTplDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setTemplateCode(po.getTemplateCode());
        dto.setTemplateName(po.getTemplateName());
        dto.setOverMode(po.getOverMode());
        dto.setOverAmount(po.getOverAmount());
        dto.setUnderMode(po.getUnderMode());
        dto.setUnderAmount(po.getUnderAmount());

        AccuracyTplToppingAccessor accuracyTplToppingAccessor = SpringAccessorUtils.getAccuracyTplToppingAccessor();
        List<AccuracyTplToppingPO> toppingPOList = accuracyTplToppingAccessor.listByTplCode(
                po.getTenantCode(), po.getTemplateCode());
        if (!CollectionUtils.isEmpty(toppingPOList)) {
            List<String> toppingCodeList = Lists.newArrayList();
            for (AccuracyTplToppingPO toppingPO : toppingPOList) {
                toppingCodeList.add(toppingPO.getToppingCode());
            }
            dto.setToppingCodeList(toppingCodeList);
        }
        return dto;
    }

    public static AccuracyTplPO convertToAccuracyTplPO(AccuracyTplPutRequest request) {
        if (request == null) {
            return null;
        }

        AccuracyTplPO po = new AccuracyTplPO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setTemplateCode(request.getTemplateCode());
        po.setTemplateName(request.getTemplateName());
        po.setOverMode(request.getOverUnit());
        po.setOverAmount(request.getOverAmount());
        po.setUnderMode(request.getUnderUnit());
        po.setUnderAmount(request.getUnderAmount());
        po.setComment(request.getComment());
        return po;
    }

    public static List<AccuracyTplToppingPO> convertToAccuracyTplToppingPO(AccuracyTplPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getToppingCodeList())) {
            return null;
        }

        return request.getToppingCodeList().stream()
                .map(toppingCode -> {
                    AccuracyTplToppingPO po = new AccuracyTplToppingPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setTemplateCode(request.getTemplateCode());
                    po.setToppingCode(toppingCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
