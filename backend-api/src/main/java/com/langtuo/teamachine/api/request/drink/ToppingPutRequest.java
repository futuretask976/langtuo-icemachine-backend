package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Map;

@Data
public class ToppingPutRequest {
    /**
     * 物料编码
     */
    private String toppingCode;

    /**
     * 物料名称
     */
    private String toppingName;

    /**
     * 物料类型编码
     */
    private String toppingTypeCode;

    /**
     * 计量单位
     */
    private int measureUnit;

    /**
     * 有效周期
     */
    private int validPeriodHour;

    /**
     * 清洗周期
     */
    private int cleanPeriodHour;

    /**
     * 转换系数
     */
    private double convertCoefficient;

    /**
     * 流速
     */
    private int flowSpeed;

    /**
     * 废料预警阈值
     */
    private int invalidWarningThresholdMin;

    /**
     * 清洗期预警阈值
     */
    private int cleanWarningThresholdMin;

    /**
     * 补料预警阈值
     */
    private int supplyWarningThreshold;

    /**
     * 溢出允许模式，0：固定值，1：百分比
     */
    private int overMode;

    /**
     * 溢出数值
     */
    private int overAmount;

    /**
     * 不及允许模式，0：固定值，1：百分比
     */
    private int underMode;

    /**
     * 不及数值
     */
    private int underAmount;

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
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidName(toppingName, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(toppingTypeCode, true)) {
            return false;
        }
        return true;
    }
}
