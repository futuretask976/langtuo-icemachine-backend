package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class ShopGroupPutRequest {
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
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 店铺组名称
     */
    private String shopGroupName;

    /**
     * 归属的组织名称
     */
    private String orgName;

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
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopGroupCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(shopGroupName, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(orgName, true)) {
            return false;
        }
        return true;
    }
}
