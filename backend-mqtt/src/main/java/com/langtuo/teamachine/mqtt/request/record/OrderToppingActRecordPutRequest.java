package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

/**
 * @author Jiaqing
 */
@Data
public class OrderToppingActRecordPutRequest {
    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     * 设备编码
     */
    private String toppingCode;

    /**
     * 实际数量
     */
    private int actualAmount;

    public boolean isValid() {
        if (!RegexUtils.isValidCode(toppingCode, true)) {
            return false;
        }
        if (stepIndex <= 0) {
            return false;
        }
        if (actualAmount <= 0) {
            return false;
        }
        return true;
    }
}
