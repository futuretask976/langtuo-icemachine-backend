package com.langtuo.teamachine.biz.excel.model;

import lombok.Data;

@Data
public class ToppingBaseRulePart {
    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     * 物料名称
     */
    private String toppingName;

    /**
     * 基础用量
     */
    private int baseAmount;
}
