package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class CleanActRecordPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息
     */
    private Map<String,String> extraInfo;

    /**
     * 幂等标记
     */
    private String idempotentMark;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 清洗开始时间
     */
    private Date cleanStartTime;

    /**
     * 清洗结束时间
     */
    private Date cleanEndTime;

    /**
     * 物料名称
     */
    private String toppingCode;

    /**
     * 管道号
     */
    private int pipelineNum;

    /**
     * 清洗方式，0：清洗规则清洗，1：手动清洗，2：营业准备规则，3：打烊规则
     */
    private int cleanType;

    /**
     * 清洗规则
     */
    private String cleanRuleCode;

    /**
     * 清洗时间（单位：秒）
     */
    private int washSec;

    /**
     * 浸泡时间（单位：分钟）
     */
    private int soakMin;

    /**
     * 浸泡期间冲洗间隔（单位：分钟）
     */
    private int flushIntervalMin;

    /**
     * 浸泡期间冲洗时间（单位：秒）
     */
    private int flushSec;

    /**
     * 回料时间（单位：秒）
     */
    private int recycleSec;

    /**
     * 清洗内容，0：冲洗，1：浸泡，2：回料
     */
    private int cleanContent;

    /**
     * 清洗剂类型，0：纯净水，1：消毒水，2：除垢剂，3：空气
     */
    private int cleanAgentType;

    /**
     * 步骤序号
     */
    private int stepIndex;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(idempotentMark, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(machineCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopGroupCode, true)) {
            return false;
        }
        if (cleanType == 0 && !RegexUtils.isValidCode(cleanRuleCode, true)) {
            return false;
        }
        if (cleanStartTime == null) {
            return false;
        }
        if (cleanEndTime == null) {
            return false;
        }
        return true;
    }
}
