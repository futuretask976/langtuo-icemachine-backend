package com.langtuo.teamachine.api.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermitActGroupDTO implements Serializable {
    /**
     * 租户编码
     */
    private String permitActGroupCode;

    /**
     * 租户名称
     */
    private String permitActGroupName;

    /**
     * 归属当前权限点组下的权限点列表
     */
    private List<PermitActDTO> permitActList;
}
