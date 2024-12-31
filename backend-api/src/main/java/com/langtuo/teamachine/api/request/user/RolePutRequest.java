package com.langtuo.teamachine.api.request.user;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RolePutRequest {
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
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 系统预留，0：不是，1：是
     */
    private int sysReserved;

    /**
     * 选中关联的权限点列表
     */
    private List<String> permitActCodeList;

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
        if (!RegexUtils.isValidCode(roleCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(roleName, true)) {
            return false;
        }
        if (!isValidPermitActCodeList()) {
            return false;
        }
        return true;
    }

    private boolean isValidPermitActCodeList() {
        if (CollectionUtils.isEmpty(permitActCodeList)) {
            return false;
        }
        for (String m : permitActCodeList) {
            if (!RegexUtils.isValidCode(m, true)) {
                return false;
            }
        }
        return true;
    }
}
