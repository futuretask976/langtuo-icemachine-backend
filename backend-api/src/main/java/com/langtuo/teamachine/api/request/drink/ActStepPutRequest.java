package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import lombok.Data;

import java.util.List;

@Data
public class ActStepPutRequest {
    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     *
     */
    private List<ToppingBaseRulePutRequest> toppingBaseRuleList;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (stepIndex <= 0) {
            return false;
        }
        if (!isValidToppingBaseRuleList()) {
            return false;
        }
        return true;
    }

    private boolean isValidToppingBaseRuleList() {
        if (CollectionUtils.isEmpty(toppingBaseRuleList)) {
            return false;
        }
        for (ToppingBaseRulePutRequest m : toppingBaseRuleList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
