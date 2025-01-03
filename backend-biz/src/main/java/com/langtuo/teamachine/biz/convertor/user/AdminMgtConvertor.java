package com.langtuo.teamachine.biz.convertor.user;

import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.request.user.AdminPutRequest;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AdminMgtConvertor {
    public static List<AdminDTO> convertToAdminDTO(List<AdminPO> poList) {
        if (CollectionUtils.isEmpty(poList) ) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToAdminDTO(po))
                .collect(Collectors.toList());
    }

    public static AdminDTO convertToAdminDTO(AdminPO adminPO) {
        if (adminPO == null) {
            return null;
        }

        AdminDTO dto = new AdminDTO();
        dto.setGmtCreated(adminPO.getGmtCreated());
        dto.setGmtModified(adminPO.getGmtModified());
        dto.setTenantCode(adminPO.getTenantCode());
        dto.setComment(adminPO.getComment());
        dto.setExtraInfo(adminPO.getExtraInfo());
        dto.setLoginName(adminPO.getLoginName());
        dto.setLoginPass(adminPO.getLoginPass());
        return dto;
    }

    public static AdminPO convertToAdminPO(AdminPutRequest request) {
        if (request == null) {
            return null;
        }

        AdminPO po = new AdminPO();
        po.setLoginName(request.getLoginName());
        po.setLoginPass(request.getLoginPass());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }
}
