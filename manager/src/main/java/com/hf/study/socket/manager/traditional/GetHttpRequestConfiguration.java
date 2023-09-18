package com.hf.study.socket.manager.traditional;

import cn.hutool.core.collection.CollectionUtil;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/9/17 23:27
 */
public class GetHttpRequestConfiguration extends ServerEndpointConfig.Configurator {

    @Override
    public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
        System.out.println("supported:" + supported);
        System.out.println("requested:" + requested);
        return super.getNegotiatedSubprotocol(supported, requested);
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> headers = request.getHeaders();
        System.out.println("headers:" + headers);
        Map<String, List<String>> parameterMap = request.getParameterMap();
        System.out.println("parameterMap = " + parameterMap);
        sec.getUserProperties().put("token", CollectionUtil.isNotEmpty(parameterMap.get("token")) ? parameterMap.get(
                "token").get(0) : null);
    }
}