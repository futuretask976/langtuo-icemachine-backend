package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

/**
 * @author Jiaqing
 */
@Data
public class OrderSpecItemActRecordPutRequest {
    /**
     * 规格编码
     */
    private String specCode;

    /**
     * 规格项编码
     */
    private String specItemCode;

    public boolean isValid() {
        if (!RegexUtils.isValidCode(specCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(specItemCode, true)) {
            return false;
        }
        return true;
    }
}
