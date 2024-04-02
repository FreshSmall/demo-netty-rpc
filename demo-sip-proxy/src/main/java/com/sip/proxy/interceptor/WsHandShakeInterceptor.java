package com.sip.proxy.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: yinchao
 * @ClassName: WsHandShakeInterceptor
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 20:50
 */
@Component
public class WsHandShakeInterceptor extends HttpSessionHandshakeInterceptor {

    /**
     * 在WebSocket连接建立之前 鉴权
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Handle before webSocket connected. ");
        attributes.put("serviceId","10030271");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // 连接后...
        System.out.println("After handle webSocket connected. ");
    }
}
