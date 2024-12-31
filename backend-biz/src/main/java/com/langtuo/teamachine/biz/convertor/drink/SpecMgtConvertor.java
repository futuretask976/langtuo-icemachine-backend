package com.langtuo.teamachine.biz.convertor.drink;

import com.langtuo.teamachine.api.model.drink.SpecDTO;
import com.langtuo.teamachine.api.model.drink.SpecItemDTO;
import com.langtuo.teamachine.api.request.drink.SpecPutRequest;
import com.langtuo.teamachine.dao.accessor.drink.SpecItemAccessor;
import com.langtuo.teamachine.dao.po.drink.SpecItemPO;
import com.langtuo.teamachine.dao.po.drink.SpecPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SpecMgtConvertor {
    public static List<SpecDTO> convertToSpecDTO(List<SpecPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<SpecDTO> list = poList.stream()
                .map(po -> convertToSpecDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static SpecDTO convertToSpecDTO(SpecPO po) {
        if (po == null) {
            return null;
        }

        SpecDTO dto = new SpecDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setSpecCode(po.getSpecCode());
        dto.setSpecName(po.getSpecName());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());

        SpecItemAccessor specItemAccessor = SpringAccessorUtils.getSpecItemAccessor();
        List<SpecItemPO> poList = specItemAccessor.listBySpecCode(po.getTenantCode(), po.getSpecCode());
        if (!CollectionUtils.isEmpty(poList)) {
            dto.setSpecItemList(poList.stream().map(item -> convertToSpecDTO(item)).collect(Collectors.toList()));
        }
        return dto;
    }

    public static List<SpecItemDTO> convertToSpecItemDTO(List<SpecItemPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToSpecDTO(po))
                .collect(Collectors.toList());
    }

    public static SpecItemDTO convertToSpecDTO(SpecItemPO po) {
        if (po == null) {
            return null;
        }

        SpecItemDTO dto = new SpecItemDTO();
        dto.setSpecCode(po.getSpecCode());
        dto.setSpecItemCode(po.getSpecItemCode());
        dto.setSpecItemName(po.getSpecItemName());
        return dto;
    }

    public static SpecPO convertToSpecDTO(SpecPutRequest request) {
        if (request == null) {
            return null;
        }

        SpecPO po = new SpecPO();
        po.setSpecCode(request.getSpecCode());
        po.setSpecName(request.getSpecName());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }

    public static List<SpecItemPO> convertToSpecItemPO(SpecPutRequest specPutRequest) {
        if (specPutRequest == null || CollectionUtils.isEmpty(specPutRequest.getSpecItemList())) {
            return null;
        }

        List<SpecItemPO> specItemPOList = specPutRequest.getSpecItemList().stream()
                .map(item -> {
                    SpecItemPO po = new SpecItemPO();
                    po.setSpecCode(specPutRequest.getSpecCode());
                    po.setTenantCode(specPutRequest.getTenantCode());
                    po.setSpecCode(specPutRequest.getSpecCode());
                    po.setSpecItemCode(item.getSpecItemCode());
                    po.setSpecItemName(item.getSpecItemName());
                    return po;
                }).collect(Collectors.toList());
        return specItemPOList;
    }
}
