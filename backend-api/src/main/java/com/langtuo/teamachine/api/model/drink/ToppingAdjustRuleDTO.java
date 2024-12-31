package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;

@Data
public class ToppingAdjustRuleDTO implements Serializable {
    public static final int ADJUST_TYPE_DECREASE = 0;
    public static final int ADJUST_TYPE_INCREASE = 1;

    /**
     * 茶品unit编码
     */
    private String teaUnitCode;

    /**
     * 步骤，从1开始
     */
    private int stepIndex;

    /**
     * 物料编码
     */
    private String toppingCode;

    /**
     * 基础用量
     */
    private int baseAmount;

    /**
     * 调整模式，0：减少，1：添加
     */
    private int adjustType;

    /**
     * 调整模式，0：固定值，1：百分比
     */
    private int adjustMode;

    /**
     * 调整用量
     */
    private int adjustAmount;

    /**
     * 物料名称
     */
    private String toppingName;

    /**
     * 计量单位，0：克，1：毫升
     */
    private int measureUnit;

    public int getActualAmount() {
        if (ADJUST_TYPE_DECREASE == adjustType) {
            return baseAmount - adjustAmount;
        } else {
            return baseAmount + adjustAmount;
        }
    }
}
