package com.langtuo.teamachine.dao.query.device;

import lombok.Data;

@Data
public class MachineGroupQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 分组名称
     */
    private String machineGroupName;
}
