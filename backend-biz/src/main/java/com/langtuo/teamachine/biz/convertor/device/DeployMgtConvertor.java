package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.DeployDTO;
import com.langtuo.teamachine.api.request.device.DeployPutRequest;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.device.DeployPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DeployMgtConvertor {
    public static DeployPO convertToDeployDTO(DeployPutRequest request) {
        if (request == null) {
            return null;
        }

        DeployPO po = new DeployPO();
        po.setDeployCode(request.getDeployCode());
        po.setModelCode(request.getModelCode());
        po.setMachineCode(request.getMachineCode());
        po.setShopCode(request.getShopCode());
        po.setState(request.getState());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(po.getExtraInfo());
        return po;
    }

    public static List<DeployDTO> convertToDeployDTO(List<DeployPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<DeployDTO> list = poList.stream()
                .map(po -> convertToDeployDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static DeployDTO convertToDeployDTO(DeployPO po) {
        if (po == null) {
            return null;
        }

        DeployDTO dto = new DeployDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTenantCode(po.getTenantCode());
        dto.setDeployCode(po.getDeployCode());
        dto.setMachineCode(po.getMachineCode());
        dto.setModelCode(po.getModelCode());
        dto.setState(po.getState());
        dto.setExtraInfo(po.getExtraInfo());

        ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
        ShopPO shopPO = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
        if (shopPO != null) {
            dto.setShopCode(shopPO.getShopCode());
            dto.setShopName(shopPO.getShopName());
        }
        return dto;
    }
}
