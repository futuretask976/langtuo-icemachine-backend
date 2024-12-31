package com.langtuo.teamachine.api.model.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermitActDTO implements Serializable {
    /**
     * 权限编码
     */
    private String permitActCode;

    /**
     * 权限名称
     */
    private String permitActName;

    /**
     * 权限组编码
     */
    private String permitActGroupCode;
}
