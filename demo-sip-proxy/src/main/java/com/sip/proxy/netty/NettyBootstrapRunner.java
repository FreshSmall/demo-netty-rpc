package com.sip.proxy.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author: yinchao
 * @ClassName: NettyBootsrapRunner
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 21:16
 */
@Component
public class NettyBootstrapRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

    @Value("${netty.websocket.port:7001}")
    private int port;

    @Value("${netty.websocket.ip:7001}")
    private String ip;

    private ApplicationContext applicationContext;

    private Channel serverChannel;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(bossGroup).channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel channel) throws Exception {
                            final ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(applicationContext.getBean(MyClientHandler.class));
                        }
                    });
            Channel channel = b.bind(port).sync().channel();
            this.serverChannel = channel;
            System.out.println("websocket 服务启动，ip=" + this.ip + ",port=" + this.port);
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        if (this.serverChannel != null) {
            this.serverChannel.close();
        }
        System.out.println("websocket 服务停止");
    }

    public void send(String msg) {
        msg = msg.replace("\n", "\r\n");
        msg = msg+"\r\n";
        final DatagramPacket data = new DatagramPacket(
                Unpooled.copiedBuffer(msg, Charset.forName("GBK")),
                new InetSocketAddress("phone1.icsoc.net", 5091));
        this.serverChannel.writeAndFlush(data);
        System.out.println("信息发送完成：data" + data);
    }
}
