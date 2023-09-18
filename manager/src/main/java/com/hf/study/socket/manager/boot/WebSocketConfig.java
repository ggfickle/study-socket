package com.hf.study.socket.manager.boot;

import cn.hutool.core.util.StrUtil;
import com.hf.study.socket.common.BizConstant;
import com.hf.tools.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws/serverTwo")//设置连接路径和处理
                .setAllowedOrigins("*")
                .addInterceptors(new MyWebSocketInterceptor());//设置拦截器
    }


    /**
     * 自定义拦截器拦截WebSocket请求
     */
    @Component
    public static class MyWebSocketInterceptor implements HandshakeInterceptor {
        //前置拦截一般用来注册用户信息，绑定 WebSocketSession
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) {
            if (request instanceof ServletServerHttpRequest) {
                HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
                String authorization = req.getHeader("Sec-WebSocket-Protocol");
                if (StrUtil.isBlank(authorization)) {
                    authorization = req.getParameter("token");
                }
                log.info("authorization = {}", authorization);
                String userId = null;
                try {
                    Claims claims = JwtUtils.parseToken(authorization);
                    userId = claims.getSubject();
                } catch (Exception e) {
                    log.error("JwtUtils.parseToken error:{}", authorization);
                }
                if (Objects.isNull(userId)){
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.warn("【beforeHandshake】 authorization Parse failure. authorization = {}", authorization);
                    return false;
                }
                //存入数据，方便在hander中获取，这里只是在方便在webSocket中存储了数据，并不是在正常的httpSession中存储，想要在平时使用的session中获得这里的数据，需要使用session 来存储一下
                attributes.put(BizConstant.SOCKET_USER_ID_NAME, userId);
                attributes.put("createTime", System.currentTimeMillis());
                attributes.put("token", authorization);
                attributes.put(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME, req.getSession().getId());
            }
            return true;
        }
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
            HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
            if (StrUtil.isNotEmpty(httpRequest.getHeader("Sec-WebSocket-Protocol"))) {
                httpResponse.addHeader("Sec-WebSocket-Protocol", httpRequest.getHeader("Sec-WebSocket-Protocol"));
            }
        }
    }
}
