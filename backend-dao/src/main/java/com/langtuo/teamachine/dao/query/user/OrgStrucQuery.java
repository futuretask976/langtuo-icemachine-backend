package com.langtuo.teamachine.dao.query.user;

import lombok.Data;

@Data
public class OrgStrucQuery {
    /**
     * 租户名称
     */
    private String tenantCode;

    /**
     * 联系人
     */
    private String orgName;
}
