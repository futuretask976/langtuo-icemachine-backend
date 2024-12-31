package com.langtuo.teamachine.biz.convertor.shop;

import com.langtuo.teamachine.api.model.shop.ShopDTO;
import com.langtuo.teamachine.api.request.shop.ShopPutRequest;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ShopMgtConvertor {
    public static List<ShopDTO> convertToShopPO(List<ShopPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<ShopDTO> list = poList.stream()
                .map(po -> convertToShopPO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static ShopDTO convertToShopPO(ShopPO po) {
        if (po == null) {
            return null;
        }

        ShopDTO dto = new ShopDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setShopCode(po.getShopCode());
        dto.setShopName(po.getShopName());
        dto.setShopType(po.getShopType());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());

        MachineGroupAccessor machineGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
        MachineGroupPO machineGroupPO = machineGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getShopGroupCode());
        if (machineGroupPO != null) {
            dto.setShopGroupCode(machineGroupPO.getMachineGroupCode());
            dto.setShopGroupName(machineGroupPO.getMachineGroupName());
        }

        return dto;
    }

    public static ShopPO convertToShopPO(ShopPutRequest request) {
        if (request == null) {
            return null;
        }

        ShopPO po = new ShopPO();
        po.setShopName(request.getShopName());
        po.setShopCode(request.getShopCode());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setShopType(request.getShopType());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }
}
