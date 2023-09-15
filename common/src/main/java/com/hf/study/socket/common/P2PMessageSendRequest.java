package com.hf.study.socket.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class P2PMessageSendRequest implements AbstractSendImMessageRequest, Serializable {

    /**
     * 发送的用户id
     */
    private Long toUserId;

    /**
     * 消息体
     */
    private String message;
}
