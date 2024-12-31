package com.langtuo.teamachine.dao.po.security;

import lombok.Data;

@Data
public class OssToken {
    /**
     *
     */
    private String region;

    /**
     *
     */
    private String bucketName;

    /**
     *
     */
    private String accessKeyId;

    /**
     *
     */
    private String accessKeySecret;

    /**
     *
     */
    private String securityToken;

    /**
     *
     */
    private String requestId;

    /**
     *
     */
    private String expiration;

    @Override
    public String toString() {
        return "STSPO{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", requestId='" + requestId + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
