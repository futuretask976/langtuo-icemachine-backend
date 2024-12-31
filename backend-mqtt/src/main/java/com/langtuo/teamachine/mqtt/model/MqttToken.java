package com.langtuo.teamachine.mqtt.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Jiaqing
 */
@Data
@Slf4j
public class MqttToken {
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
