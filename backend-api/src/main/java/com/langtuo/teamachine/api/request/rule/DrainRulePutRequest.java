package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DrainRulePutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 开业规则编码
     */
    private String drainRuleCode;

    /**
     * 开业规则名称
     */
    private String drainRuleName;

    /**
     * 包括物料列表
     */
    private List<DrainRuleToppingPutRequest> toppingRuleList;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(drainRuleCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(drainRuleName, true)) {
            return false;
        }
        if (!isValidToppingRuleList()) {
            return false;
        }
        return true;
    }

    private boolean isValidToppingRuleList() {
        if (CollectionUtils.isEmpty(toppingRuleList)) {
            return false;
        }
        for (DrainRuleToppingPutRequest m : toppingRuleList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
