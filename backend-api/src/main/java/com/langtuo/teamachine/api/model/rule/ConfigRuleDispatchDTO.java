package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConfigRuleDispatchDTO implements Serializable {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 配置规则编码
     */
    private String configRuleCode;

    /**
     * 机器分组编码列表
     */
    private List<String> machineGroupCodeList;
}
