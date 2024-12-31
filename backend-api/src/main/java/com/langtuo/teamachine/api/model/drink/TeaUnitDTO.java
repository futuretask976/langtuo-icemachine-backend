package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeaUnitDTO implements Serializable {
    /**
     * 茶品单位编码
     */
    private String teaUnitCode;

    /**
     * 茶品单位名称
     */
    private String teaUnitName;

    /**
     * 外部茶品编码
     */
    private String outerTeaUnitCode;

    /**
     *
     */
    private List<ToppingAdjustRuleDTO> toppingAdjustRuleList;
}
