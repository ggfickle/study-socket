package com.hf.study.socket.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class FacadeSendMessageRequest implements Serializable {

    /**
     * 1：全员消息，2：P2P
     */
    private Integer type;

    /**
     * 发送的用户id
     */
    private Long toUserId;

    /**
     * 消息体
     */
    private String message;
}
