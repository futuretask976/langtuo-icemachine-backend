package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.po.device.MachinePO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class MachineManager {
    @Resource
    private MachineAccessor machineAccessor;

    /**
     * 获取指定店铺编码列表下的机器列表
     * @param tenantCode
     * @param shopCodeList
     * @return
     */
    public List<String> getMachineCodeListByShopCodeList(String tenantCode, List<String> shopCodeList) {
        if (StringUtils.isBlank(tenantCode) || CollectionUtils.isEmpty(shopCodeList)) {
            return null;
        }

        List<String> machineCodeList = shopCodeList.stream()
                .map(shopCode -> {
                    List<MachinePO> machinePOList = machineAccessor.listByShopCode(tenantCode, shopCode);
                    if (CollectionUtils.isEmpty(machinePOList)) {
                        return null;
                    }
                    return machinePOList.stream()
                            .map(shop -> shop.getMachineCode())
                            .collect(Collectors.toList());
                })
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return machineCodeList;
    }
}
