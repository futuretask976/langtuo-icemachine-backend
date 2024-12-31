package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class WarningRulePutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 预警规则编码
     */
    private String warningRuleCode;

    /**
     * 预警规则名称
     */
    private String warningRuleName;

    /**
     * 预警类型，0：强提醒，1：弱提醒
     */
    private int warningType;

    /**
     * 预警内容，0：报废预警，1：缺料预警，2：清洗预警
     */
    private int warningContent;

    /**
     * 阈值类型，0：绝对值，1：百分比
     */
    private int thresholdMode;

    /**
     * 阈值
     */
    private int threshold;

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
        if (!RegexUtils.isValidCode(warningRuleCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(warningRuleName, true)) {
            return false;
        }
        return true;
    }
}
