/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.sip.proxy.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

/**
 * @author wudongwu@baijiahulian.com
 * @description
 * @team wuhan operational dev.
 * @date 2020/11/26 下午3:55
 **/
public class WsInfo {

    private WebSocketSession webSocketSession;
    Date updateTime;


    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
