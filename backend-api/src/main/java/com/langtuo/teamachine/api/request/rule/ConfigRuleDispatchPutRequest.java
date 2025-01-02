package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;

@Data
public class ConfigRuleDispatchPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 配置规则编码
     */
    private String configRuleCode;

    /**
     * 机器分组编码列表
     */
    private List<String> machineGroupCodeList;

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
        if (!isValidShopGroupCodeList()) {
            return false;
        }
        return true;
    }

    private boolean isValidShopGroupCodeList() {
        if (CollectionUtils.isEmpty(machineGroupCodeList)) {
            return false;
        }
        for (String m : machineGroupCodeList) {
            if (!RegexUtils.isValidCode(m, true)) {
                return false;
            }
        }
        return true;
    }
}
