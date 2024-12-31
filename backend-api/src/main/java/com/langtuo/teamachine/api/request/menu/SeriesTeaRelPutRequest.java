package com.langtuo.teamachine.api.request.menu;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

@Data
public class SeriesTeaRelPutRequest {
    /**
     * 系列编码
     */
    private String seriesCode;

    /**
     * 茶品编码
     */
    private String teaCode;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(seriesCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaCode, true)) {
            return false;
        }
        return true;
    }
}
