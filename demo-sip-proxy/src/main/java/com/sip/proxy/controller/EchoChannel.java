package com.sip.proxy.controller;

import com.sip.proxy.service.MsgSendService;
import com.sip.proxy.util.WsPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author: yinchao
 * @ClassName: WsHandleController
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 15:40
 */
@RestController
public class EchoChannel implements WebSocketHandler {

    @Autowired
    private MsgSendService sendService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established");
        WsPool.addSession(session);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        final String msg = webSocketMessage.getPayload().toString();
        System.out.println("websocket msg:"+msg);
        sendService.send(msg);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        System.out.println("Error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed");

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

