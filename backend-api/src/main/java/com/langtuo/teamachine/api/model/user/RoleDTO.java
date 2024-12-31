package com.langtuo.teamachine.api.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class RoleDTO implements Serializable {
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
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 系统预留，0：不是，1：是
     */
    private int sysReserved;

    /**
     * 关联当前角色的管理员数量
     */
    private int adminCount;

    /**
     * 关联的权限点列表
     */
    private List<String> permitActCodeList;
}
