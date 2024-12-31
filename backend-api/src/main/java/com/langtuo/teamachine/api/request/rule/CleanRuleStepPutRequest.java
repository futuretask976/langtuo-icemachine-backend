package com.langtuo.teamachine.api.request.rule;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

@Data
public class CleanRuleStepPutRequest {
    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     * 清洗内容，0：冲洗，1：浸泡，2：回料
     */
    private int cleanContent;

    /**
     * 冲洗时间
     */
    private int washSec;

    /**
     * 浸泡时间
     */
    private int soakMin;

    /**
     * 浸泡冲洗间隔时间
     */
    private int flushIntervalMin;

    /**
     * 浸泡冲洗时间
     */
    private int flushSec;

    /**
     * 回料时间（单位：秒）
     */
    private int recycleSec;

    /**
     * 提醒标题
     */
    private String remindTitle;

    /**
     * 提醒内容
     */
    private String remindContent;

    /**
     * 是否需要再次确认
     */
    private int needConfirm;

    /**
     * 清洗剂类型，0：纯净水，1：消毒水，2：除垢剂，3：空气
     */
    private int cleanAgentType;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (stepIndex <= 0) {
            return false;
        }
        if (!isValid4NeedConfirm()) {
            return false;
        }
        return true;
    }

    private boolean isValid4NeedConfirm () {
        if (needConfirm == 1) {
            if (!RegexUtils.isValidName(remindTitle, true)
                    || !RegexUtils.isValidComment(remindContent, true)) {
                return false;
            }
        }
        return true;
    }
}
