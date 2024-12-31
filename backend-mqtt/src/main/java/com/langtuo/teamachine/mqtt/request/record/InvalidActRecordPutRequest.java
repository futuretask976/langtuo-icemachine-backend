package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author Jiaqing
 */
@Data
public class InvalidActRecordPutRequest {
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
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 物料名称
     */
    private String toppingCode;

    /**
     * 管道序号
     */
    private int pipelineNum;

    /**
     * 失效数量
     */
    private int invalidAmount;

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
        if (!RegexUtils.isValidCode(toppingCode, true)) {
            return false;
        }
        if (invalidTime == null) {
            return false;
        }
        if (pipelineNum <= 0) {
            return false;
        }
        return true;
    }
}
