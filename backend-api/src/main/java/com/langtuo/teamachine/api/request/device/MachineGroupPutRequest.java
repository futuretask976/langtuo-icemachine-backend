package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class MachineGroupPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 备注
     */
    private String comment;

    /**
     * 机器分组编码
     */
    private String machineGroupCode;

    /**
     * 机器分组名称
     */
    private String machineGroupName;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidCode(machineGroupCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(machineGroupName, true)) {
            return false;
        }
        return true;
    }
}
