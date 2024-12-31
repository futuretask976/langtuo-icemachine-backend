package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopGroupAccessor;
import com.langtuo.teamachine.dao.po.device.DeployPO;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.po.shop.ShopGroupPO;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MachineMgtConvertor {
    public static List<MachineDTO> convert(List<MachinePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(MachineMgtConvertor::convert)
                .collect(Collectors.toList());
    }

    public static MachineDTO convert(MachinePO po) {
        if (po == null) {
            return null;
        }

        MachineDTO dto = new MachineDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTenantCode(po.getTenantCode());
        dto.setMachineCode(po.getMachineCode());
        dto.setElecBoardCode(po.getElecBoardCode());
        dto.setScreenCode(po.getScreenCode());
        dto.setModelCode(po.getModelCode());
        dto.setMachineName(po.getMachineName());
        dto.setMaintainUntil(po.getMaintainUntil());
        dto.setValidUntil(po.getValidUntil());
        dto.setState(po.getState());
        dto.setOnlineState(po.getOnlineState());
        dto.setExtraInfo(po.getExtraInfo());

        ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
        ShopPO shopPO = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
        if (shopPO != null) {
            dto.setShopCode(shopPO.getShopCode());
            dto.setShopName(shopPO.getShopName());
        }

        ShopGroupAccessor shopGroupAccessor = SpringAccessorUtils.getShopGroupAccessor();
        ShopGroupPO shopGroupPO = shopGroupAccessor.getByShopGroupCode(po.getTenantCode(),
                shopPO.getShopGroupCode());
        if (shopGroupPO != null) {
            dto.setShopGroupCode(shopGroupPO.getShopGroupCode());
            dto.setShopGroupName(shopGroupPO.getShopGroupName());
        }
        return dto;
    }

    public static MachinePO convertToMachinePO(MachineActivatePutRequest request, DeployPO deployPO) {
        if (request == null || deployPO == null) {
            return null;
        }

        MachinePO po = new MachinePO();
        po.setTenantCode(deployPO.getTenantCode());
        po.setMachineCode(deployPO.getMachineCode());
        po.setScreenCode(request.getScreenCode());
        po.setElecBoardCode(request.getElecBoardCode());
        po.setModelCode(deployPO.getModelCode());
        po.setShopCode(deployPO.getShopCode());
        po.setMachineName(null);
        po.setState(CommonConsts.STATE_ENABLED);
        po.setOnlineState(request.getOnlineState());
        po.setMaintainUntil(null);
        po.setValidUntil(null);
        return po;
    }

    public static MachinePO convert(MachineUpdatePutRequest request) {
        if (request == null) {
            return null;
        }

        MachinePO po = new MachinePO();
        po.setMachineCode(request.getMachineCode());
        po.setScreenCode(request.getScreenCode());
        po.setElecBoardCode(request.getElecBoardCode());
        po.setModelCode(request.getModelCode());
        po.setMachineName(request.getMachineName());
        po.setMaintainUntil(request.getMaintainUntil());
        po.setValidUntil(request.getValidUntil());
        po.setState(request.getState());
        po.setOnlineState(request.getOnlineState());
        po.setShopCode(request.getShopCode());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        return po;
    }
}
