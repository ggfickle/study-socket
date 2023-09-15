package com.hf.study.socket.manager.imBiz;

import com.hf.study.socket.common.FanoutMessageSendRequest;
import com.hf.study.socket.common.P2PMessageSendRequest;
import com.hf.study.socket.manager.boot.MyWebSocketHandler;
import com.hf.study.socket.service.ImMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImMessageManager {

    private final ImMessageService messageService;

    public boolean sendP2P(P2PMessageSendRequest request) {
        boolean sendMessage = MyWebSocketHandler.p2pSendMessage(request.getToUserId(), request.getMessage());
        return sendMessage;
    }

    public boolean fanoutSend(FanoutMessageSendRequest request) {
        MyWebSocketHandler.fanoutMessage(request.getMessage());
        return true;
    }
}
