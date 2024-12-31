package com.langtuo.teamachine.web.model;

import lombok.Data;

import java.util.List;

/**
 * @author Jiaqing
 */
@Data
public class LoginSuccessDTO {
    /**
     * 登录用 JWT 令牌
     */
    private String jwtToken;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 关联的权限点列表
     */
    private List<String> permitActCodeList;
}
