package com.langtuo.teamachine.biz.excel.model;

import lombok.Data;

@Data
public class ToppingAdjustRulePart {
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
     * 实际用量
     */
    private int actualAmount;
}
