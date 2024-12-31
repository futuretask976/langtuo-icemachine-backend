package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

@Data
public class DrainRuleToppingPutRequest {
    /**
     * 物料名称
     */
    private String toppingCode;

    /**
     * 排空时间（单位：秒）
     */
    private int flushSec;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(toppingCode, true)) {
            return false;
        }
        if (flushSec <= 0) {
            return false;
        }
        return true;
    }
}
