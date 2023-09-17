package com.hf.study.socket.manager.boot;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hf.study.socket.common.BizConstant;
import com.hf.study.socket.common.FacadeSendMessageRequest;
import com.hf.tools.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MyWebSocketHandler implements WebSocketHandler, ApplicationContextAware {
    private static final Map<Long, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    private static ApplicationContext APPLICATION_CONTEXT;

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getCurrentUserId(session);
        SESSIONS.put(userId, session);
        log.info("userId:{}成功建立连接, 当前在线用户数:{}", userId, getOnlineNumberIds().size());
//        ImMessageService messageService = APPLICATION_CONTEXT.getBean(ImMessageService.class);
//        List<ImMessage> offlineMessage = messageService.findOfflineMessage(userId);
//        if (CollectionUtil.isNotEmpty(offlineMessage)) {
//            log.info("拉取发送用户{}的离线消息", userId);
//            offlineMessage.forEach(item -> p2pSendMessage(item.getToUserId(), item.getMessage()));
//        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        String msg = message.getPayload().toString();
        log.info("收到{}发送的消息:{}", getCurrentUserId(session), msg);
        FacadeSendMessageRequest messageRequest = JSONUtil.toBean(msg, FacadeSendMessageRequest.class);
        if (Objects.equals(messageRequest.getType(), 1)) {
            // 全员消息
            fanoutMessage(messageRequest.getMessage());
        } else if(Objects.equals(messageRequest.getType(), 2)) {
            // P2P消息
            p2pSendMessage(messageRequest.getToUserId(), messageRequest.getMessage());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("连接出错，", exception);
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("userId:{}连接已关闭,status:{}", getCurrentUserId(session), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     *
     * @param message
     */
    public static boolean p2pSendMessage(Long toUserId, String message) {
        WebSocketSession webSocketSession = SESSIONS.get(toUserId);
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            return false;
        }
        try {
            webSocketSession.sendMessage(new TextMessage(message));
            log.info("消息发送成功：userId:{}, message:{}, ", toUserId, message);
        } catch (IOException e) {
            log.error("消息发送失败：userId:{}, message:{}, ", toUserId, message, e);
            throw new BizException("消息发送失败");
        }
        return true;
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public static void fanoutMessage(String message) {
        SESSIONS.keySet().forEach(us -> p2pSendMessage(us, message));
    }

    /**
     * 获取当前在线用户列表
     *
     * @return
     */
    public static Set<Long> getOnlineNumberIds() {
        return new HashSet<>(SESSIONS.keySet());
    }

    /**
     * 获取当前用户id
     *
     * @param session
     * @return
     */
    private Long getCurrentUserId(WebSocketSession session) {
        String userId = session.getAttributes().get(BizConstant.SOCKET_USER_ID_NAME).toString();
        if (StrUtil.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }
}
