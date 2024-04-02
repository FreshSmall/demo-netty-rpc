package com.net.udp.netty_client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

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
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        String msg = packet.content().toString(Charset.forName("GBK"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP客户端接收到消息：" + msg);
        if (msg.contains("401 Unauthorized")) {
            // 重新发送注册请求
            final String message = "";
            final byte[] bytes = message.getBytes(Charset.forName("GBK"));
            final DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes),
                    packet.sender());
            channelHandlerContext.writeAndFlush(data).sync();
        }
    }
}
