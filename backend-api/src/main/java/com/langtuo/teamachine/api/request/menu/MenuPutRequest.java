package com.langtuo.teamachine.api.request.menu;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class MenuPutRequest {
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
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 生效时间
     */
    private Date validFrom;

    /**
     * 菜单-系列关系
     */
    private List<MenuSeriesRelPutRequest> menuSeriesRelList;

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
        if (!RegexUtils.isValidCode(menuCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(menuName, true)) {
            return false;
        }
        if (!isValidPipelineList()) {
            return false;
        }
        if (validFrom == null) {
            return false;
        }
        return true;
    }

    private boolean isValidPipelineList() {
        if (CollectionUtils.isEmpty(menuSeriesRelList)) {
            return false;
        }
        for (MenuSeriesRelPutRequest m : menuSeriesRelList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
