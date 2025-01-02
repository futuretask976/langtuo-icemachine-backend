package com.langtuo.teamachine.biz.convertor.record;

import com.langtuo.teamachine.api.model.record.ActRecordDTO;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.po.record.ActRecordPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ActRecordMgtConvertor {
    public static List<ActRecordDTO> convertToCleanActRecordDTO(List<ActRecordPO> poList, boolean fillDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<ActRecordDTO> list = poList.stream()
                .map(po -> convertToCleanActRecordDTO(po, fillDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static ActRecordDTO convertToCleanActRecordDTO(ActRecordPO po, boolean fillDetail) {
        if (po == null) {
            return null;
        }

        ActRecordDTO dto = new ActRecordDTO();
        dto.setExtraInfo(po.getExtraInfo());
        dto.setIdempotentMark(po.getIdempotentMark());
        dto.setMachineCode(po.getMachineCode());
        dto.setMachineGroupCode(po.getMachineGroupCode());

        if (fillDetail) {
            MachineGroupAccessor machineGroupAccessor = SpringAccessorUtils.getMachineGroupAccessor();
            MachineGroupPO machineGroupPO = machineGroupAccessor.getByMachineGroupCode(po.getTenantCode(),
                    po.getMachineGroupCode());
            if (machineGroupPO != null) {
                dto.setMachineGroupName(machineGroupPO.getMachineGroupName());
            }
        }
        return dto;
    }
}
