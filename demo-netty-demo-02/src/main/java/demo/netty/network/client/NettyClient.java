/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package demo.netty.network.client;

import demo.netty.network.codec.ObjDecoder;
import demo.netty.network.codec.ObjEncoder;
import demo.netty.network.msg.Request;
import demo.netty.network.msg.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/21 11:43
 **/
public class NettyClient implements Runnable {

    // 配置服务端NIO线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture channelFuture;

    @Override
    public void run() {
        ChannelFuture f = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            pipeline.addLast(new ObjDecoder(Response.class));
                            pipeline.addLast(new ObjEncoder(Request.class));
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            f = b.connect("localhost", 8083).syncUninterruptibly();
            System.out.println("netty client system start done");
            this.channelFuture = f;
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }


}
