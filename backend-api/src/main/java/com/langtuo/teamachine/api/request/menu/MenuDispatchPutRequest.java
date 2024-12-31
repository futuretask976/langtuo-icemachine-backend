package com.langtuo.teamachine.api.request.menu;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;

@Data
public class MenuDispatchPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 店铺编码
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
        if (!RegexUtils.isValidCode(menuCode, true)) {
            return false;
        }
        if (!isValidShopGroupList()) {
            return false;
        }
        return true;
    }

    private boolean isValidShopGroupList() {
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
