package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;

@Data
public class WarningRuleDispatchPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 预警规则编码
     */
    private String warningRuleCode;

    /**
     * 店铺组编码列表
     */
    private List<String> shopGroupCodeList;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(warningRuleCode, true)) {
            return false;
        }
        if (!isValidShopGroupCodeList()) {
            return false;
        }
        return true;
    }

    private boolean isValidShopGroupCodeList() {
        if (CollectionUtils.isEmpty(shopGroupCodeList)) {
            return false;
        }
        for (String m : shopGroupCodeList) {
            if (!RegexUtils.isValidCode(m, true)) {
                return false;
            }
        }
        return true;
    }
}