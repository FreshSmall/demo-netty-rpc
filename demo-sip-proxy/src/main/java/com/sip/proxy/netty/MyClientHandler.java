package com.sip.proxy.netty;

import com.sip.proxy.service.MsgReceiveService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: yinchao
 * @ClassName: MyClientHandler
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 14:39
 */
@Component
@ChannelHandler.Sharable
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Autowired
    private MsgReceiveService msgReceiveService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        String msg = packet.content().toString(Charset.forName("GBK"));
        System.out.println("netty receive msg:"+msg);
        msgReceiveService.receive(msg);
    }
}
