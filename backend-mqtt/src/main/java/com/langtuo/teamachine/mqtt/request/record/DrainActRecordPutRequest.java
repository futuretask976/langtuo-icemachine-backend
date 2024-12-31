package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author Jiaqing
 */
@Data
public class DrainActRecordPutRequest {
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
    private Date drainStartTime;

    /**
     * 清洗结束时间
     */
    private Date drainEndTime;

    /**
     * 物料名称
     */
    private String toppingCode;

    /**
     * 管道序号
     */
    private int pipelineNum;

    /**
     * 清洗方式，0：排空规则排空，1：手动排空
     */
    private int drainType;

    /**
     * 开业规则编码
     */
    private String drainRuleCode;

    /**
     * 排空时间（单位：秒）
     */
    private int flushSec;

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
        if (drainStartTime == null) {
            return false;
        }
        if (drainEndTime == null) {
            return false;
        }
        return true;
    }
}
