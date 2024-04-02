package com.sip.proxy.service;

import com.sip.proxy.dto.WsSessionInfo;
import com.sip.proxy.util.WsPool;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: yinchao
 * @ClassName: MsgReceiveService
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 21:07
 */
@Service
public class MsgReceiveService {


    /**
     * 获取 sip 服务端的信令，发送给websocket
     *
     * @param msg
     */
    public void receive(String msg) {

        final Set<WsSessionInfo> allWsInfo = WsPool.getAllWsInfo("10030271");
        for (WsSessionInfo wsSessionInfo : allWsInfo) {
            WsPool.send(wsSessionInfo, msg);
        }
    }
}
