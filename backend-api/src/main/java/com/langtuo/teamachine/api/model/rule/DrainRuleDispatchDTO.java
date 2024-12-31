package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DrainRuleDispatchDTO implements Serializable {
    /**
     * 开业规则编码
     */
    private String drainRuleCode;

    /**
     * 店铺组编码列表
     */
    private List<String> shopGroupCodeList;
}
