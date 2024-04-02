/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.sip.proxy.util;

import com.sip.proxy.dto.WsInfo;
import com.sip.proxy.dto.WsSessionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author liujialiang
 * @description
 * @team wuhan operational dev.
 * @date 2020/5/12 11:45 上午
 **/
@Slf4j
public class WsPool {

    // 本地缓存session连接
    private static Map<String, WsInfo> Session_Map = new ConcurrentHashMap<>();
    private static Map<String, Set<WsSessionInfo>> Account_Map = new ConcurrentHashMap<>();

    public static Integer getOnlineNum() {
        return Session_Map.size();
    }

    public static Set<WsSessionInfo> getAllWsInfo(String serviceId) {
        return Account_Map.get(serviceId);
    }


    public static WebSocketSession getSessionById(String wsConnectionId) {
        WsInfo wsInfo = Session_Map.get(wsConnectionId);
        return wsInfo.getWebSocketSession();
    }

    public static void addAccountMap(WsSessionInfo wsSessionInfo) {
        Set<WsSessionInfo> wsSessionInfos = Account_Map.get(wsSessionInfo.getServiceId());
        if (wsSessionInfos == null) {
            wsSessionInfos = new CopyOnWriteArraySet<>();
            Account_Map.put(wsSessionInfo.getServiceId(), wsSessionInfos);
        }
        wsSessionInfos.add(wsSessionInfo);
    }

    public static void removeAccountMap(WsSessionInfo wsSessionInfo) {
        String businessId = wsSessionInfo.getBusinessId();
        String serviceId = wsSessionInfo.getServiceId();
        String cno = wsSessionInfo.getCno();
        String key = serviceId;
        Set<WsSessionInfo> wsSessionInfos = Account_Map.get(key);
        if (wsSessionInfos == null) {
            return;
        }
        wsSessionInfos.remove(wsSessionInfo);
        if (wsSessionInfos.isEmpty()) {
            Account_Map.remove(key);
        }
    }

    public static void addSession(WebSocketSession session) throws IOException {
        WsSessionInfo wsSessionInfo = getAccount(session);
        addAccountMap(wsSessionInfo);
        WsInfo wsInfo = new WsInfo();
        wsInfo.setWebSocketSession(session);
        wsInfo.setUpdateTime(new Date());
        Session_Map.put(wsSessionInfo.getServiceId(), wsInfo);
    }

    public static WebSocketSession getSession(WsSessionInfo wsSessionInfo) {
        WsInfo wsInfo = Session_Map.get(wsSessionInfo.getServiceId());
        if (wsInfo != null) {
            return wsInfo.getWebSocketSession();
        }
        return null;
    }

    public static void removeSession(WebSocketSession session) {
        if (ObjectUtils.isEmpty(session)) {
            return;
        }
        WsSessionInfo wsSessionInfo = getAccount(session);
        try {
            session.close();
            // 关闭后移除
            Session_Map.remove(wsSessionInfo.getWsConnectionId());
            removeAccountMap(wsSessionInfo);
        } catch (IOException e) {
            log.error("关闭连接出现错误:wsSessionInfo={} session={}", wsSessionInfo, session);
        }
    }

    /**
     * 推送消息,单接口数据就推送
     *
     * @param wsSessionInfo
     * @param message
     */
    public static void send(WsSessionInfo wsSessionInfo, String message) {
        WebSocketSession session = getSession(wsSessionInfo);
        if (null == session) {
            log.error("该账号session为null:wsSessionInfo={} message={}", wsSessionInfo, message);
        }
        if (!session.isOpen()) {
            log.error("该账号session已经关闭:wsSessionInfo={} message={}");
            return;
        }
        synchronized (session) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("ws 消息发送异常：{}", e);
            }
        }
    }

    public static WsSessionInfo getAccount(final WebSocketSession session) {
        String serviceId = (String) session.getAttributes().get("serviceId");
        String businessId = (String) session.getAttributes().get("businessId");
        String wsConnectionId = (String) session.getAttributes().get("wsConnectionId");
        String cno = (String) session.getAttributes().get("cno");
        return new WsSessionInfo(serviceId, cno, businessId, wsConnectionId);
    }
}
