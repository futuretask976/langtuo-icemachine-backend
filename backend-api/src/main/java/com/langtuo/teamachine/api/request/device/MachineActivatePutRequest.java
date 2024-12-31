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
     * 部署码激活
     */
    private String deployCode;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 屏幕编码
     */
    private String screenCode;

    /**
     * 电控板编码
     */
    private String elecBoardCode;

    /**
     * 在线状态，0：离线，1：在线
     */
    private int onlineState;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(deployCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(machineCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(screenCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(elecBoardCode, true)) {
            return false;
        }
        return true;
    }
}
