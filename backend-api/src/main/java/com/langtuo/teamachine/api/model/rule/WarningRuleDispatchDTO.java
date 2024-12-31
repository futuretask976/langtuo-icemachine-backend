package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WarningRuleDispatchDTO implements Serializable {
    /**
     * 预警规则编码
     */
    private String warningRuleCode;

    /**
     * 店铺组编码列表
     */
    private List<String> shopGroupCodeList;
}
