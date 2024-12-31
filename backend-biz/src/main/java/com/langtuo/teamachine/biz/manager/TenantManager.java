package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.user.TenantAccessor;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户 Manager
 * @author Jiaqing
 */
@Component
public class TenantManager {
    @Resource
    private TenantAccessor tenantAccessor;

    public List<String> getTenantCodeList() {
        List<TenantPO> list = tenantAccessor.list();

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .map(TenantPO::getTenantCode)
                .collect(Collectors.toList());
    }
}
