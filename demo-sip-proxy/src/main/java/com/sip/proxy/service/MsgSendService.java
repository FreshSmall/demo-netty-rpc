package com.sip.proxy.service;

import com.sip.proxy.netty.NettyBootstrapRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yinchao
 * @ClassName: MsgService
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 21:06
 */
@Service
public class MsgSendService {

    @Autowired
    private NettyBootstrapRunner nettyBootstrapRunner;

    /**
     * websocket sip 信令发送给sip服务端
     * @param msg
     */
    public void send(String msg) {
        nettyBootstrapRunner.send(msg);
    }
}
