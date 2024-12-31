package com.langtuo.teamachine.api.model.record;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OrderActRecordDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

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
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 店铺组名称
     */
    private String shopGroupName;

    /**
     * 订单生成时间
     */
    private Date orderGmtCreated;

    /**
     * 茶品类型名称
     */
    private String teaTypeName;

    /**
     * 茶品名称
     */
    private String teaName;

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
    private List<OrderSpecItemActRecordDTO> specItemList;

    /**
     * 物料列表
     */
    private List<OrderToppingActRecordDTO> toppingList;
}
