package com.langtuo.teamachine.dao.node.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrgNode {
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
     * 组织名称
     */
    private String orgName;

    /**
     * 父节点名称
     */
    private String parentOrgName;

    /**
     * 父节点
     */
    private OrgNode parent;

    /**
     * 子节点列表
     */
    private List<OrgNode> children;
}
