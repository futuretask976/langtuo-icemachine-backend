package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class MachineUpdatePutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 机器名称
     */
    private String machineName;

    /**
     * 屏幕编码
     */
    private String screenCode;

    /**
     * 电控板编码
     */
    private String elecBoardCode;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 机器状态，0：禁用，1：启用
     */
    private int state;

    /**
     * 有效期
     */
    private Date validUntil;

    /**
     * 保修期
     */
    private Date maintainUntil;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 在线状态，0：离线，1：在线
     */
    private int onlineState;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(machineCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(machineName, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(screenCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(elecBoardCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopCode, true)) {
            return false;
        }
        if (validUntil == null) {
            return false;
        }
        if (maintainUntil == null) {
            return false;
        }
        return true;
    }
}
