package com.langtuo.teamachine.dao.po.user;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class AdminPO {
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
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPass;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 组织名称
     */
    private String orgName;
}
