package com.sip.proxy.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.stereotype.Component;

/**
 * @author: yinchao
 * @ClassName: MyChannelInitializer
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 14:37
 */
@Component
@ChannelHandler.Sharable
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(new MyClientHandler());
    }
}
