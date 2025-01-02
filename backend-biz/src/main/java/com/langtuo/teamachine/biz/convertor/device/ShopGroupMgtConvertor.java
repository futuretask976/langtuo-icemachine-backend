package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.MachineGroupDTO;
import com.langtuo.teamachine.api.request.device.ShopGroupPutRequest;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ShopGroupMgtConvertor {
    public static List<MachineGroupDTO> convert(List<MachineGroupPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<MachineGroupDTO> list = poList.stream()
                .map(po -> convert(po))
                .collect(Collectors.toList());
        return list;
    }

    public static MachineGroupDTO convert(MachineGroupPO po) {
        if (po == null) {
            return null;
        }

        MachineGroupDTO dto = new MachineGroupDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setMachineGroupCode(po.getMachineGroupCode());
        dto.setMachineGroupName(po.getMachineGroupName());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setOrgName(po.getOrgName());

        ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
        int shopCount = shopAccessor.countByShopGroupCode(po.getTenantCode(), po.getMachineGroupCode());
        dto.setShopCount(shopCount);

        return dto;
    }

    public static MachineGroupPO convert(ShopGroupPutRequest request) {
        if (request == null) {
            return null;
        }

        MachineGroupPO po = new MachineGroupPO();
        po.setMachineGroupName(request.getShopGroupName());
        po.setMachineGroupCode(request.getShopGroupCode());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setOrgName(request.getOrgName());
        return po;
    }
}
