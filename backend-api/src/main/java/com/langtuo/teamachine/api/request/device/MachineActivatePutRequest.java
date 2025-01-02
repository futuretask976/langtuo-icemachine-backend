package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class MachineActivatePutRequest {
    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 在线状态，0：离线，1：在线
     */
    private int onlineState;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(machineCode, true)) {
            return false;
        }
        return true;
    }
}
