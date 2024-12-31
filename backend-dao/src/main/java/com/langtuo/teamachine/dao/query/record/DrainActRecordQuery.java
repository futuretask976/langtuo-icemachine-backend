package com.langtuo.teamachine.dao.query.record;

import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class DrainActRecordQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 店铺组编码列表
     */
    private List<String> shopGroupCodeList;

    /**
     * 店铺编码列表
     */
    private List<String> shopCodeList;

    /**
     * 添加店铺编码
     * @param shopGroupCodeList
     */
    public void addAllShopGroupCode(List<String> shopGroupCodeList) {
        if (CollectionUtils.isEmpty(shopGroupCodeList)) {
            return;
        }
        if (this.shopGroupCodeList == null) {
            this.shopGroupCodeList = Lists.newArrayList();
        }
        this.shopGroupCodeList.addAll(shopGroupCodeList);
    }

    /**
     * 添加店铺编码
     * @param shopCodeList
     */
    public void addAllShopCode(List<String> shopCodeList) {
        if (CollectionUtils.isEmpty(shopCodeList)) {
            return;
        }
        if (this.shopCodeList == null) {
            this.shopCodeList = Lists.newArrayList();
        }
        this.shopCodeList.addAll(shopCodeList);
    }
}
