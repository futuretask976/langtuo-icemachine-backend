package com.langtuo.teamachine.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorMsgDTO implements Serializable {
    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;
}
