package com.langtuo.teamachine.dao.query.record;

import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class ActRecordQuery {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 店铺组编码列表
     */
    private List<String> machineGroupCodeList;

    /**
     * 添加店铺编码
     * @param machineGroupCodeList
     */
    public void addAllMachineGroupCode(List<String> machineGroupCodeList) {
        if (CollectionUtils.isEmpty(machineGroupCodeList)) {
            return;
        }
        if (this.machineGroupCodeList == null) {
            this.machineGroupCodeList = Lists.newArrayList();
        }
        this.machineGroupCodeList.addAll(machineGroupCodeList);
    }
}
