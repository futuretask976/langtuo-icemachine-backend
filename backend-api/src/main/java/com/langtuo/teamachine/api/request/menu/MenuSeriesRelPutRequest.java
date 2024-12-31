package com.langtuo.teamachine.api.request.menu;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

@Data
public class MenuSeriesRelPutRequest {
    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 系列编码
     */
    private String seriesCode;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(menuCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(seriesCode, true)) {
            return false;
        }
        return true;
    }
}
