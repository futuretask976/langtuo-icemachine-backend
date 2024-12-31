package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpecItemRuleDTO implements Serializable {
    /**
     * 规格编码
     */
    private String specCode;

    /**
     * 规格名换
     */
    private String specName;

    /**
     * 规格项编码
     */
    private String specItemCode;

    /**
     * 规格项名称
     */
    private String specItemName;
}
