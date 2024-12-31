package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class TeaUnitPutRequest {
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
    private List<ToppingAdjustRulePutRequest> toppingAdjustRuleList;

    public void addToppingAdjustRulePutRequest(ToppingAdjustRulePutRequest request) {
        if (request == null) {
            return;
        }
        if (this.toppingAdjustRuleList == null) {
            this.toppingAdjustRuleList = Lists.newArrayList();
        }
        this.toppingAdjustRuleList.add(request);
    }

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidLongCode(teaUnitCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidLongName(teaUnitName, true)) {
            return false;
        }
        if (!isValidToppingAdjustRuleList()) {
            return false;
        }
        return true;
    }

    private boolean isValidToppingAdjustRuleList() {
        if (CollectionUtils.isEmpty(toppingAdjustRuleList)) {
            return false;
        }
        for (ToppingAdjustRulePutRequest m : toppingAdjustRuleList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
