package com.net.udp;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.netty.channel.socket.DatagramPacket;

import java.util.List;

/**
 * @author: yinchao
 * @ClassName: UdpReceiver
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/3/28 10:50
 */
public class UdpReceiver {


    private static final int R_PORT = 2222;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel channel) throws Exception {
                        channel.pipeline().addLast(new MyUdpDecoder());
                    }
                });

        try {
            Channel channel = bootstrap.bind(R_PORT).sync().channel();
            // 等待 channel 关闭
            channel.closeFuture().sync();
            // 关闭 EventLoopGroup
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class MyUdpDecoder extends MessageToMessageDecoder<DatagramPacket> {
        @Override
        protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
            ByteBuf buf = packet.content();
            String msg = buf.toString(CharsetUtil.UTF_8);
            System.out.println("Received message: " + msg);
        }
    }
}
