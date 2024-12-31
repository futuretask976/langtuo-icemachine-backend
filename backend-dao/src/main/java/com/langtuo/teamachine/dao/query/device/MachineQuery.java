package com.langtuo.teamachine.dao.query.device;

import lombok.Data;

import java.util.List;

@Data
public class MachineQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 分组编码
     */
    private List<String> machineGroupCodeList;
}
