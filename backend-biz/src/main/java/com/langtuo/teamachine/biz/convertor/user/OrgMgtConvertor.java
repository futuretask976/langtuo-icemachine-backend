package com.langtuo.teamachine.biz.convertor.user;

import com.langtuo.teamachine.api.model.user.OrgDTO;
import com.langtuo.teamachine.api.request.user.OrgPutRequest;
import com.langtuo.teamachine.dao.node.user.OrgNode;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrgMgtConvertor {
    public static List<OrgDTO> convertToOrgDTO(List<OrgNode> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<OrgDTO> list = poList.stream()
                .map(po -> convertToOrgDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static OrgDTO convertToOrgDTO(OrgNode node) {
        if (node == null) {
            return null;
        }

        OrgDTO dto = new OrgDTO();
        dto.setGmtCreated(node.getGmtCreated());
        dto.setGmtModified(node.getGmtModified());
        dto.setOrgName(node.getOrgName());
        dto.setParentOrgName(node.getParentOrgName());

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            List<OrgDTO> children = Lists.newArrayList();
            for (OrgNode orgNode : node.getChildren()) {
                children.add(convertToOrgDTO(orgNode));
            }
            dto.setChildren(children);
        }
        return dto;
    }

    public static OrgNode convertToOrgNode(OrgPutRequest request) {
        if (request == null) {
            return null;
        }

        OrgNode node = new OrgNode();
        node.setTenantCode(request.getTenantCode());
        node.setOrgName(request.getOrgName());
        node.setParentOrgName(request.getParentOrgName());
        return node;
    }
}
