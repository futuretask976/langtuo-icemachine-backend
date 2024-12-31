package com.langtuo.teamachine.api.model.drink;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActStepDTO implements Serializable {
    /**
     *
     */
    private int stepIndex;

    /**
     *
     */
    private List<ToppingBaseRuleDTO> toppingBaseRuleList;

    /**
     * 添加物料基础规则
     * @param dto
     */
    public void addToppingBaseRule(ToppingBaseRuleDTO dto) {
        if (dto == null) {
            return;
        }
        if (toppingBaseRuleList == null) {
            toppingBaseRuleList = Lists.newArrayList();
        }
        toppingBaseRuleList.add(dto);
    }
}
