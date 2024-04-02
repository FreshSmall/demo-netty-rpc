package com.net.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: yinchao
 * @ClassName: UdpSender
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/3/28 10:50
 */
public class UdpSender {

    private static final int S_PORT = 0;
    private static final int R_PORT = 2222;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel channel) throws Exception {
                        channel.pipeline().addLast(new MyUdpEncoder());
                    }
                });

        try {
            Channel channel = bootstrap.bind(S_PORT).sync().channel();
            for (int i = 0; i < 10; i++) {
                // 发送数据
                channel.writeAndFlush("Send msg: " + i);
                System.out.println("Send msg: " + i);
            }
            // 等待 channel 关闭
            channel.closeFuture().sync();
            // 关闭 EventLoopGroup
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 编码器，将要发送的消息（这里是一个 String）封装到一个 DatagramPacket 中
    private static class MyUdpEncoder extends MessageToMessageEncoder<String> {
        private final InetSocketAddress remoteAddress = new InetSocketAddress(HOSTNAME, R_PORT);

        @Override
        protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            ByteBuf buf = ctx.alloc().buffer(bytes.length);
            buf.writeBytes(bytes);
            DatagramPacket packet = new DatagramPacket(buf, remoteAddress);
            out.add(packet);
        }
    }
}
