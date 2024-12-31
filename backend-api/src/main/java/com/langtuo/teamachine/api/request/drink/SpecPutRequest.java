package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SpecPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

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
    private List<SpecItemPutRequest> specItemList;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidCode(specCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(specName, true)) {
            return false;
        }
        if (!isValidSpecItem()) {
            return false;
        }
        return true;
    }

    private boolean isValidSpecItem() {
        if (CollectionUtils.isEmpty(specItemList)) {
            return false;
        }
        for (SpecItemPutRequest s : specItemList) {
            if (!s.isValid()) {
                return false;
            }
        }
        return true;
    }
}
