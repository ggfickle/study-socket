package com.hf.study.socket.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class FanoutMessageSendRequest implements AbstractSendImMessageRequest, Serializable {

    /**
     * 消息体
     */
    private String message;
}
