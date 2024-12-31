package com.langtuo.teamachine.api.request.user;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
public class AdminPutRequest {
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
     * 管理员登录名
     */
    private String loginName;

    /**
     * 管理员登录密码
     */
    private String loginPass;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 组织名称
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
        if (!RegexUtils.isValidName(loginName, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(roleCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(orgName, true)) {
            return false;
        }
        if (putNew && StringUtils.isBlank(loginPass)) {
            return false;
        }
        return true;
    }
}
