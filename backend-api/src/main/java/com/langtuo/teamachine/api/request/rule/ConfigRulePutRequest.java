package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class ConfigRulePutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 清洁规则编码
     */
    private String configRuleCode;

    /**
     * 清洁规则名称
     */
    private String configRuleName;

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
        if (!RegexUtils.isValidCode(configRuleCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(configRuleName, true)) {
            return false;
        }
        return true;
    }
}
