package com.langtuo.teamachine.api.model.drink;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecRuleDTO implements Serializable {
    /**
     * 规格编码
     */
    private String specCode;

    /**
     * 规格名称
     */
    private String specName;

    /**
     *
     */
    private List<SpecItemRuleDTO> specItemRuleList;

    public void addSpecItemRule(SpecItemRuleDTO specItemRuleDTO) {
        if (specItemRuleDTO == null) {
            return;
        }

        if (specItemRuleList == null) {
            specItemRuleList = Lists.newArrayList();
        }
        specItemRuleList.add(specItemRuleDTO);
    }
}
