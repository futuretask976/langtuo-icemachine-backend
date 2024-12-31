package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class DeployPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 部署编码
     */
    private String deployCode;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 部署状态，0：未部署，1：已部署
     */
    private int state;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(deployCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(modelCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(machineCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopCode, true)) {
            return false;
        }
        return true;
    }
}
