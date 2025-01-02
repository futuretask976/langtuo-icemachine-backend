package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.MachineGroupDTO;
import com.langtuo.teamachine.api.request.device.MachineGroupPutRequest;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MachineGroupMgtConvertor {
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
        dto.setExtraInfo(po.getExtraInfo());
        dto.setComment(po.getComment());
        dto.setMachineGroupCode(po.getMachineGroupCode());
        dto.setMachineGroupName(po.getMachineGroupName());

        return dto;
    }

    public static MachineGroupPO convert(MachineGroupPutRequest request) {
        if (request == null) {
            return null;
        }

        MachineGroupPO po = new MachineGroupPO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setMachineGroupName(request.getMachineGroupName());
        po.setMachineGroupCode(request.getMachineGroupCode());
        po.setComment(request.getComment());
        return po;
    }
}
