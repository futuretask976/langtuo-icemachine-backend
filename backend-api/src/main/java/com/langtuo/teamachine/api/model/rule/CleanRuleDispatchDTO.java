package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CleanRuleDispatchDTO implements Serializable {
    /**
     * 清洁规则编码
     */
    private String cleanRuleCode;

    /**
     * 店铺组编码列表
     */
    private List<String> shopGroupCodeList;
}
