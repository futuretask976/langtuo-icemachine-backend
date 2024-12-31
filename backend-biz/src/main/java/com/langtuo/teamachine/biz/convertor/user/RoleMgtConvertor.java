package com.langtuo.teamachine.biz.convertor.user;

import com.langtuo.teamachine.api.model.user.RoleDTO;
import com.langtuo.teamachine.api.request.user.RolePutRequest;
import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMgtConvertor {
    public static List<RoleDTO> convertToRoleDTO(List<RolePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<RoleDTO> list = poList.stream()
                .map(po -> convertToRoleDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static RoleDTO convertToRoleDTO(RolePO po) {
        if (po == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setRoleCode(po.getRoleCode());
        dto.setRoleName(po.getRoleName());
        dto.setSysReserved(po.getSysReserved());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());

        RoleActRelAccessor roleActRelAccessor = SpringAccessorUtils.getRoleActRelAccessor();
        List<RoleActRelPO> roleActRelPOList = roleActRelAccessor.listByRoleCode(
                po.getTenantCode(), po.getRoleCode());
        if (!CollectionUtils.isEmpty(roleActRelPOList)) {
            dto.setPermitActCodeList(roleActRelPOList.stream()
                    .map(item -> item.getPermitActCode())
                    .collect(Collectors.toList()));
        }

        AdminAccessor adminAccessor = SpringAccessorUtils.getAdminAccessor();
        int adminCount = adminAccessor.countByRoleCode(po.getTenantCode(), po.getRoleCode());
        dto.setAdminCount(adminCount);

        return dto;
    }

    public static RolePO convertToRolePO(RolePutRequest request) {
        if (request == null) {
            return null;
        }

        RolePO po = new RolePO();
        po.setRoleCode(request.getRoleCode());
        po.setRoleName(request.getRoleName());
        po.setSysReserved(request.getSysReserved());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }

    public static List<RoleActRelPO> convertRoleActRel(RolePutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getPermitActCodeList())) {
            return null;
        }

        return request.getPermitActCodeList().stream().map(item -> {
            RoleActRelPO po = new RoleActRelPO();
            po.setRoleCode(request.getRoleCode());
            po.setTenantCode(request.getTenantCode());
            po.setPermitActCode(item);
            return po;
        }).collect(Collectors.toList());
    }
}
