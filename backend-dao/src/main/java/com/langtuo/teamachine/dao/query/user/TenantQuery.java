package com.langtuo.teamachine.dao.query.user;

import lombok.Data;

@Data
public class TenantQuery {
    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 联系人
     */
    private String contactPerson;
}
