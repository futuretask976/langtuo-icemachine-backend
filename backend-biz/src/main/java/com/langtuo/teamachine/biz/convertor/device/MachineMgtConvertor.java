package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.MachineDTO;
import com.langtuo.teamachine.api.request.device.MachineUpdatePutRequest;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
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
        dto.setExtraInfo(po.getExtraInfo());
        dto.setMachineCode(po.getMachineCode());
        dto.setMachineName(po.getMachineName());
        dto.setState(po.getState());
        dto.setOnlineState(po.getOnlineState());

        MachineGroupAccessor machineGroupAccessor = SpringAccessorUtils.getMachineGroupAccessor();
        MachineGroupPO machineGroupPO = machineGroupAccessor.getByMachineGroupCode(po.getTenantCode(),
                po.getMachineGroupCode());
        if (machineGroupPO != null) {
            dto.setMachineGroupCode(machineGroupPO.getMachineGroupCode());
            dto.setMachineGroupName(machineGroupPO.getMachineGroupName());
        }
        return dto;
    }

    public static MachinePO convert(MachineUpdatePutRequest request) {
        if (request == null) {
            return null;
        }

        MachinePO po = new MachinePO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setMachineCode(request.getMachineCode());
        po.setMachineName(request.getMachineName());
        po.setState(request.getState());
        po.setOnlineState(request.getOnlineState());
        po.setMachineGroupCode(request.getMachineGroupCode());
        return po;
    }
}
