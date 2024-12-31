package com.langtuo.teamachine.api.model.security;

import lombok.Data;

import java.util.Date;

@Data
public class MqttTokenDTO {
    /**
     * MQTT 连接用户名
     */
    private String accessKey;

    /**
     * MQTT 令牌
     */
    private String accessToken;

    /**
     * 令牌过期时间
     */
    private Date expiration;
}
