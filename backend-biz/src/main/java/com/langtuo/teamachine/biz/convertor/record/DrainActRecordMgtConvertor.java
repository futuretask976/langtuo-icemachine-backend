package com.langtuo.teamachine.biz.convertor.record;

import com.langtuo.teamachine.api.model.record.DrainActRecordDTO;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.dao.po.record.ActRecordPO;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DrainActRecordMgtConvertor {
    public static List<DrainActRecordDTO> convertToDrainActRecordDTO(List<ActRecordPO> poList, boolean fillDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<DrainActRecordDTO> list = poList.stream()
                .map(po -> convertToDrainActRecordDTO(po, fillDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static DrainActRecordDTO convertToDrainActRecordDTO(ActRecordPO po, boolean fillDetail) {
        if (po == null) {
            return null;
        }

        DrainActRecordDTO dto = new DrainActRecordDTO();
        dto.setExtraInfo(po.getExtraInfo());
        dto.setIdempotentMark(po.getIdempotentMark());
        dto.setMachineCode(po.getMachineCode());
        dto.setShopCode(po.getShopCode());
        dto.setShopGroupCode(po.getMachineGroupCode());
        dto.setDrainStartTime(po.getDrainStartTime());
        dto.setDrainEndTime(po.getDrainEndTime());
        dto.setToppingCode(po.getToppingCode());
        dto.setPipelineNum(po.getPipelineNum());
        dto.setDrainType(po.getDrainType());
        dto.setDrainRuleCode(po.getDrainRuleCode());
        dto.setFlushSec(po.getFlushSec());

        if (fillDetail) {
            if (!StringUtils.isBlank(po.getToppingCode())) {
                ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
                ToppingPO toppingPO = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
                if (toppingPO != null) {
                    dto.setToppingName(toppingPO.getToppingName());
                }
            }

            MachineGroupAccessor machineGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
            MachineGroupPO machineGroupPO = machineGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getMachineGroupCode());
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
