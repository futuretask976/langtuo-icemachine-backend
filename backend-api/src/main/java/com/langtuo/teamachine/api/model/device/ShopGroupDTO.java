package com.langtuo.teamachine.api.model.device;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class ShopGroupDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 店铺组编码
     */
    private String shopGroupCode;

    /**
     * 店铺组名称
     */
    private String shopGroupName;

    /**
     * 归属的组织名称
     */
    private String orgName;

    /**
     * 关联当前类型的店铺数量
     */
    private int shopCount;
}
