package com.langtuo.teamachine.biz.convertor.user;

import com.langtuo.teamachine.api.model.user.TenantDTO;
import com.langtuo.teamachine.api.request.user.TenantPutRequest;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TenantMgtConvertor {
    public static List<TenantDTO> convertToTenantDTO(List<TenantPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<TenantDTO> list = poList.stream()
                .map(po -> convertToTenantDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static TenantDTO convertToTenantDTO(TenantPO po) {
        if (po == null) {
            return null;
        }

        TenantDTO dto = new TenantDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTenantCode(po.getTenantCode());
        dto.setTenantName(po.getTenantName());
        dto.setContactPerson(po.getContactPerson());
        dto.setContactPhone(po.getContactPhone());
        dto.setComment(po.getComment());
        po.setExtraInfo(po.getExtraInfo());
        return dto;
    }

    public static TenantPO convertToTenantPO(TenantPutRequest request) {
        if (request == null) {
            return null;
        }

        TenantPO po = new TenantPO();
        po.setTenantCode(request.getTenantCode());
        po.setTenantName(request.getTenantName());
        po.setContactPerson(request.getContactPerson());
        po.setContactPhone(request.getContactPhone());
        po.setComment(request.getComment());
        po.setExtraInfo(po.getExtraInfo());
        return po;
    }
}
