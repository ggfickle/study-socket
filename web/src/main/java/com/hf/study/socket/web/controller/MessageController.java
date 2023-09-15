package com.hf.study.socket.web.controller;

import com.hf.study.socket.common.FanoutMessageSendRequest;
import com.hf.study.socket.common.P2PMessageSendRequest;
import com.hf.study.socket.manager.imBiz.ImMessageManager;
import com.hf.tools.common.entity.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final ImMessageManager messageManager;

    @PostMapping("/p2p")
    public ResponseData<Boolean> sendP2P(@RequestBody P2PMessageSendRequest request) {
        return ResponseData.success(messageManager.sendP2P(request));
    }

    @PostMapping("/fanout")
    public ResponseData<Boolean> sendFanout(@RequestBody FanoutMessageSendRequest request) {
        return ResponseData.success(messageManager.fanoutSend(request));
    }

}
