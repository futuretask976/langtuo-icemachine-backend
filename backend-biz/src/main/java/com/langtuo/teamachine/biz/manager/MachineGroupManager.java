package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class MachineGroupManager {
    @Resource
    private AdminManager adminManager;

    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    public List<String> getMachineGroupCodeList(String tenantCode) {
        List<MachineGroupPO> machineGroupPOList = machineGroupAccessor.list(tenantCode);
        if (CollectionUtils.isEmpty(machineGroupPOList)) {
            return null;
        }

        List<String> machineGroupCodeList = machineGroupPOList.stream()
                .map(MachineGroupPO::getMachineGroupCode)
                .collect(Collectors.toList());
        return machineGroupCodeList;
    }
}
