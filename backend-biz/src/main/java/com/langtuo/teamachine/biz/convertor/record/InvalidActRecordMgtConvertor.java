package com.langtuo.teamachine.biz.convertor.record;

import com.langtuo.teamachine.api.model.record.InvalidActRecordDTO;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopGroupAccessor;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidActRecordMgtConvertor {
    public static List<InvalidActRecordDTO> convertToInvalidActRecordDTO(List<InvalidActRecordPO> poList, boolean fillDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<InvalidActRecordDTO> list = poList.stream()
                .map(po -> convertToInvalidActRecordDTO(po, fillDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static InvalidActRecordDTO convertToInvalidActRecordDTO(InvalidActRecordPO po, boolean fillDetail) {
        if (po == null) {
            return null;
        }

        InvalidActRecordDTO dto = new InvalidActRecordDTO();
        dto.setExtraInfo(po.getExtraInfo());
        dto.setIdempotentMark(po.getIdempotentMark());
        dto.setMachineCode(po.getMachineCode());
        dto.setShopCode(po.getShopCode());
        dto.setShopGroupCode(po.getShopGroupCode());
        dto.setInvalidTime(po.getInvalidTime());
        dto.setToppingCode(po.getToppingCode());
        dto.setPipelineNum(po.getPipelineNum());
        dto.setInvalidAmount(po.getInvalidAmount());

        if (fillDetail) {
            ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
            ToppingPO toppingPO = toppingAccessor.getByToppingCode(
                    po.getTenantCode(), po.getToppingCode());
            if (toppingPO != null) {
                dto.setToppingName(toppingPO.getToppingName());
            }

            ShopGroupAccessor shopGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
            MachineGroupPO machineGroupPO = shopGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getShopGroupCode());
            if (machineGroupPO != null) {
                dto.setShopGroupName(machineGroupPO.getMachineGroupName());
            }

            ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
            ShopPO shopPO = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
            if (shopPO != null) {
                dto.setShopName(shopPO.getShopName());
            }
        }
        return dto;
    }
}
