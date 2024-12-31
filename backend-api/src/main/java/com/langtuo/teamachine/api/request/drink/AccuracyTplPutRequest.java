package com.langtuo.teamachine.api.request.drink;

import com.langtuo.teamachine.api.request.device.ModelPipelinePutRequest;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AccuracyTplPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 溢出允许单位，0：固定值，1：百分比
     */
    private int overUnit;

    /**
     * 溢出数值
     */
    private int overAmount;

    /**
     * 不及允许单位，0：固定值，1：百分比
     */
    private int underUnit;

    /**
     * 不及数值
     */
    private int underAmount;

    /**
     * 应用物料编码
     */
    private List<String> toppingCodeList;

    /**
     * 备注
     */
    private String comment;

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
        if (!RegexUtils.isValidCode(templateCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(templateName, true)) {
            return false;
        }
        if (!isValidToppingCodeList()) {
            return false;
        }
        return true;
    }

    private boolean isValidToppingCodeList() {
        if (CollectionUtils.isEmpty(toppingCodeList)) {
            return false;
        }
        for (String m : toppingCodeList) {
            if (!RegexUtils.isValidCode(m, true)) {
                return false;
            }
        }
        return true;
    }
}
