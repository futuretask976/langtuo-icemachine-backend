package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;

@Data
public class ToppingBaseRuleDTO implements Serializable {
    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     * 物料编码
     */
    private String toppingCode;

    /**
     * 物料名称
     */
    private String toppingName;

    /**
     * 基础用量
     */
    private int baseAmount;

    /**
     * 计量单位
     */
    private int measureUnit;
}
