package com.langtuo.teamachine.biz.convertor.record;

import com.langtuo.teamachine.api.model.record.CleanActRecordDTO;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ActRecordMgtConvertor {
    public static List<CleanActRecordDTO> convertToCleanActRecordDTO(List<CleanActRecordPO> poList, boolean fillDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<CleanActRecordDTO> list = poList.stream()
                .map(po -> convertToCleanActRecordDTO(po, fillDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static CleanActRecordDTO convertToCleanActRecordDTO(CleanActRecordPO po, boolean fillDetail) {
        if (po == null) {
            return null;
        }

        CleanActRecordDTO dto = new CleanActRecordDTO();
        dto.setExtraInfo(po.getExtraInfo());
        dto.setIdempotentMark(po.getIdempotentMark());
        dto.setMachineCode(po.getMachineCode());
        dto.setShopCode(po.getShopCode());
        dto.setShopGroupCode(po.getShopGroupCode());
        dto.setCleanStartTime(po.getCleanStartTime());
        dto.setCleanEndTime(po.getCleanEndTime());
        dto.setToppingCode(po.getToppingCode());
        dto.setPipelineNum(po.getPipelineNum());
        dto.setCleanType(po.getCleanType());
        dto.setCleanRuleCode(po.getCleanRuleCode());
        dto.setWashSec(po.getWashSec());
        dto.setSoakMin(po.getSoakMin());
        dto.setFlushSec(po.getFlushSec());
        dto.setFlushIntervalMin(po.getFlushIntervalMin());
        dto.setRecycleSec(po.getRecycleSec());
        dto.setCleanContent(po.getCleanContent());
        dto.setCleanAgentType(po.getCleanAgentType());
        dto.setStepIndex(po.getStepIndex());

        if (fillDetail) {
            if (po.getToppingCode() != null) {
                ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
                ToppingPO toppingPO = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
                if (toppingPO != null) {
                    dto.setToppingName(toppingPO.getToppingName());
                }
            }

            MachineGroupAccessor machineGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
            MachineGroupPO machineGroupPO = machineGroupAccessor.getByShopGroupCode(po.getTenantCode(), po.getShopGroupCode());
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