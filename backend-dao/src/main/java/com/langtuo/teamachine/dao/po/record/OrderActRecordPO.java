package com.langtuo.teamachine.dao.po.record;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class OrderActRecordPO {
    /**
     * 数据表id
     */
    private long id;

    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 额外信息，格式：a:b;c:d
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
}
