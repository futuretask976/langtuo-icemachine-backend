package com.langtuo.teamachine.biz.convertor.record;

import com.langtuo.teamachine.api.model.record.OrderActRecordDTO;
import com.langtuo.teamachine.api.model.record.OrderSpecItemActRecordDTO;
import com.langtuo.teamachine.api.model.record.OrderToppingActRecordDTO;
import com.langtuo.teamachine.dao.accessor.drink.*;
import com.langtuo.teamachine.dao.accessor.record.OrderSpecItemActRecordAccessor;
import com.langtuo.teamachine.dao.accessor.record.OrderToppingActRecordAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopGroupAccessor;
import com.langtuo.teamachine.dao.po.drink.*;
import com.langtuo.teamachine.dao.po.record.OrderActRecordPO;
import com.langtuo.teamachine.dao.po.record.OrderSpecItemActRecordPO;
import com.langtuo.teamachine.dao.po.record.OrderToppingActRecordPO;
import com.langtuo.teamachine.dao.po.shop.MachineGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderActRecordMgtConvertor {
    public static List<OrderActRecordDTO> convertToOrderActRecordDTO(List<OrderActRecordPO> poList, boolean fillDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<OrderActRecordDTO> list = poList.stream()
                .map(po -> convertToOrderActRecordDTO(po, fillDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static OrderActRecordDTO convertToOrderActRecordDTO(OrderActRecordPO po, boolean fillDetail) {
        if (po == null) {
            return null;
        }

        OrderActRecordDTO dto = new OrderActRecordDTO();
        dto.setExtraInfo(po.getExtraInfo());
        dto.setIdempotentMark(po.getIdempotentMark());
        dto.setMachineCode(po.getMachineCode());
        dto.setShopCode(po.getShopCode());
        dto.setShopGroupCode(po.getShopGroupCode());
        dto.setOrderGmtCreated(po.getOrderGmtCreated());
        dto.setOuterOrderId(po.getOuterOrderId());
        dto.setState(po.getState());

        TeaAccessor teaAccessor = SpringAccessorUtils.getTeaAccessor();
        TeaPO teaPO = teaAccessor.getByTeaCode(po.getTenantCode(), po.getTeaCode());
        if (teaPO != null) {
            dto.setTeaName(teaPO.getTeaName());
        }

        if (fillDetail) {
            TeaTypeAccessor teaTypeAccessor = SpringAccessorUtils.getTeaTypeAccessor();
            TeaTypePO teaTypePO = teaTypeAccessor.getByTeaTypeCode(po.getTenantCode(), po.getTeaTypeCode());
            if (teaTypePO != null) {
                dto.setTeaTypeName(teaTypePO.getTeaTypeName());
            }

            ShopGroupAccessor shopGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
            MachineGroupPO machineGroupPO = shopGroupAccessor.getByShopGroupCode(
                    po.getTenantCode(), po.getShopGroupCode());
            if (machineGroupPO != null) {
                dto.setShopGroupName(machineGroupPO.getShopGroupName());
            }

            ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
            ShopPO shopPO = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
            if (shopPO != null) {
                dto.setShopName(shopPO.getShopName());
            }

            OrderSpecItemActRecordAccessor orderSpecItemActRecordAccessor = SpringAccessorUtils.getOrderSpecItemActRecordAccessor();
            List<OrderSpecItemActRecordPO> specItemActRecordList = orderSpecItemActRecordAccessor.listByIdempotentMark(
                    po.getTenantCode(), po.getShopGroupCode(), po.getIdempotentMark());
            dto.setSpecItemList(convertToOrderSpecItemActRecordDTO(specItemActRecordList));

            OrderToppingActRecordAccessor orderToppingActRecordAccessor = SpringAccessorUtils.getOrderToppingActRecordAccessor();
            List<OrderToppingActRecordPO> toppingActRecordList = orderToppingActRecordAccessor.listByIdempotentMark(
                    po.getTenantCode(), po.getShopGroupCode(), po.getIdempotentMark());
            dto.setToppingList(convertToOrderToppingActRecordDTO((toppingActRecordList)));
        }
        return dto;
    }

    public static List<OrderSpecItemActRecordDTO> convertToOrderSpecItemActRecordDTO(List<OrderSpecItemActRecordPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToOrderActRecordDTO(po))
                .collect(Collectors.toList());
    }

    public static OrderSpecItemActRecordDTO convertToOrderActRecordDTO(OrderSpecItemActRecordPO po) {
        if (po == null) {
            return null;
        }

        OrderSpecItemActRecordDTO dto = new OrderSpecItemActRecordDTO();
        dto.setSpecCode(po.getSpecCode());
        dto.setSpecItemCode(po.getSpecItemCode());

        SpecAccessor specAccessor = SpringAccessorUtils.getSpecAccessor();
        SpecPO specPO = specAccessor.getBySpecCode(po.getTenantCode(), po.getSpecCode());
        dto.setSpecName(specPO.getSpecName());

        SpecItemAccessor specItemAccessor = SpringAccessorUtils.getSpecItemAccessor();
        SpecItemPO specItemPO = specItemAccessor.getBySpecItemCode(po.getTenantCode(), po.getSpecItemCode());
        dto.setSpecItemName(specItemPO.getSpecItemName());
        return dto;
    }

    public static List<OrderToppingActRecordDTO> convertToOrderToppingActRecordDTO(List<OrderToppingActRecordPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToOrderActRecordDTO(po))
                .collect(Collectors.toList());
    }

    public static OrderToppingActRecordDTO convertToOrderActRecordDTO(OrderToppingActRecordPO po) {
        if (po == null) {
            return null;
        }

        OrderToppingActRecordDTO dto = new OrderToppingActRecordDTO();
        dto.setStepIndex(po.getStepIndex());
        dto.setToppingCode(po.getToppingCode());
        dto.setActualAmount(po.getActualAmount());

        ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
        ToppingPO toppingPO = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
        dto.setToppingName(toppingPO.getToppingName());
        return dto;
    }
}
