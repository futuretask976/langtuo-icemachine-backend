package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class SpecRulePutRequest {
    /**
     * 规格编码
     */
    private String specCode;

    /**
     * 规格项编码
     */
    private List<SpecItemRulePutRequest> specItemRuleList;

    public void addSpecItemRulePutRequest(SpecItemRulePutRequest request) {
        if (request == null) {
            return;
        }
        if (specItemRuleList == null) {
            specItemRuleList = Lists.newArrayList();
        }
        specItemRuleList.add(request);
    }

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(specCode, true)) {
            return false;
        }
        if (!isValidSpecItemRuleList()) {
            return false;
        }
        return true;
    }

    private boolean isValidSpecItemRuleList() {
        if (CollectionUtils.isEmpty(specItemRuleList)) {
            return false;
        }
        for (SpecItemRulePutRequest m : specItemRuleList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
