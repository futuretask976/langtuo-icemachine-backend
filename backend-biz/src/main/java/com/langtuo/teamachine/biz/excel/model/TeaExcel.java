package com.langtuo.teamachine.biz.excel.model;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class TeaExcel {
    /**
     *
     */
    private TeaInfoPart teaInfoPart;

    /**
     *
     */
    private List<TeaUnitPart> teaUnitPartList;

    /**
     *
     */
    private List<ToppingAdjustRulePart> toppingAdjustRulePartList;

    public void addTeaUnit(List<TeaUnitPart> teaUnitPartList) {
        if (teaUnitPartList == null) {
            return;
        }
        if (this.teaUnitPartList == null) {
            this.teaUnitPartList = Lists.newArrayList();
        }
        this.teaUnitPartList.addAll(teaUnitPartList);
    }

    public void addAdjustRulePart(List<ToppingAdjustRulePart> toppingAdjustRulePartList) {
        if (toppingAdjustRulePartList == null) {
            return;
        }
        if (this.toppingAdjustRulePartList == null) {
            this.toppingAdjustRulePartList = Lists.newArrayList();
        }
        this.toppingAdjustRulePartList.addAll(toppingAdjustRulePartList);
    }
}