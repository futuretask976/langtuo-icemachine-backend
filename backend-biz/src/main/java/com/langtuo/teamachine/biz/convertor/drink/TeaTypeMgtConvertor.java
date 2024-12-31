package com.langtuo.teamachine.biz.convertor.drink;

import com.langtuo.teamachine.api.model.drink.*;
import com.langtuo.teamachine.api.request.drink.TeaTypePutRequest;
import com.langtuo.teamachine.dao.accessor.drink.*;
import com.langtuo.teamachine.dao.po.drink.*;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TeaTypeMgtConvertor {
    public static List<TeaTypeDTO> convertToTeaTypePO(List<TeaTypePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<TeaTypeDTO> list = poList.stream()
                .map(po -> convertToTeaTypePO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static TeaTypeDTO convertToTeaTypePO(TeaTypePO po) {
        if (po == null) {
            return null;
        }

        TeaTypeDTO dto = new TeaTypeDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTeaTypeCode(po.getTeaTypeCode());
        dto.setTeaTypeName(po.getTeaTypeName());
        dto.setComment(po.getComment());
        po.setExtraInfo(po.getExtraInfo());

        TeaAccessor teaAccessor = SpringAccessorUtils.getTeaAccessor();
        int teaCount = teaAccessor.countByTeaTypeCode(po.getTenantCode(), po.getTeaTypeCode());
        dto.setTeaCount(teaCount);

        return dto;
    }

    public static TeaTypePO convertToTeaTypePO(TeaTypePutRequest request) {
        if (request == null) {
            return null;
        }

        TeaTypePO po = new TeaTypePO();
        po.setTeaTypeCode(request.getTeaTypeCode());
        po.setTeaTypeName(request.getTeaTypeName());
        po.setTenantCode(request.getTenantCode());
        po.setComment(request.getComment());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }
}
