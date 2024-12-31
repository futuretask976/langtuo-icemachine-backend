package com.langtuo.teamachine.mqtt.request.record;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jiaqing
 */
@Data
public class OrderActRecordPutRequest {
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
     * 设备编码
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
     * 订单生成时间
     */
    private Date orderGmtCreated;

    /**
     * 茶品类型编码
     */
    private String teaTypeCode;

    /**
     * 茶品编码
     */
    private String teaCode;

    /**
     * 茶品单位编码
     */
    private String teaUnitCode;

    /**
     * 外部订单id
     */
    private String outerOrderId;

    /**
     * 状态，0：未制作，1：制作中，2：已制作，3：有异常，4：已取消
     */
    private int state;

    /**
     * 规格项列表
     */
    private List<OrderSpecItemActRecordPutRequest> specItemList;

    /**
     * 物料列表
     */
    private List<OrderToppingActRecordPutRequest> toppingList;

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
        if (!RegexUtils.isValidCode(shopGroupCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(shopCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaTypeCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaUnitCode, true)) {
            return false;
        }
        if (!isValidSpecItemList()) {
            return false;
        }
        if (!isValidToppingList()) {
            return false;
        }
        return true;
    }

    private boolean isValidSpecItemList() {
        if (CollectionUtils.isEmpty(specItemList)) {
            return false;
        }
        for (OrderSpecItemActRecordPutRequest m : specItemList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidToppingList() {
        if (CollectionUtils.isEmpty(toppingList)) {
            return false;
        }
        for (OrderToppingActRecordPutRequest m : toppingList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
