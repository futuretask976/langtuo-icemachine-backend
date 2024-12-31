package com.langtuo.teamachine.biz.convertor.drink;

import com.langtuo.teamachine.api.model.drink.ToppingTypeDTO;
import com.langtuo.teamachine.api.request.drink.ToppingTypePutRequest;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.po.drink.ToppingTypePO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ToppingTypeMgtConvertor {
    public static List<ToppingTypeDTO> convertToToppingTypeDTO(List<ToppingTypePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToToppingTypeDTO(po))
                .collect(Collectors.toList());
    }

    public static ToppingTypeDTO convertToToppingTypeDTO(ToppingTypePO po) {
        if (po == null) {
            return null;
        }

        ToppingTypeDTO dto = new ToppingTypeDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setToppingTypeCode(po.getToppingTypeCode());
        dto.setToppingTypeName(po.getToppingTypeName());
        dto.setComment(po.getComment());
        po.setExtraInfo(po.getExtraInfo());

        ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
        int toppingCount = toppingAccessor.countByToppingTypeCode(po.getTenantCode(), po.getToppingTypeCode());
        dto.setToppingCount(toppingCount);

        return dto;
    }

    public static ToppingTypePO convertToToppingTypeDTO(ToppingTypePutRequest request) {
        if (request == null) {
            return null;
        }

        ToppingTypePO po = new ToppingTypePO();
        po.setToppingTypeCode(request.getToppingTypeCode());
        po.setToppingTypeName(request.getToppingTypeName());
        po.setTenantCode(request.getTenantCode());
        po.setComment(request.getComment());
        po.setExtraInfo(po.getExtraInfo());
        return po;
    }
}
