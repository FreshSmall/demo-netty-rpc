package com.net.udp.netty_client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author: yinchao
 * @ClassName: NettyClient
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 14:42
 */
public class NettyClient {

    public static void main(String[] args) {
       /* String msg = "REGISTER sip:phone1.icsoc.net:5091 SIP/2.0\r\n" +
                        "Via: SIP/2.0/UDP 192.168.1.9:52770;rport;branch=z9hG4bKiHwSB5wtZ\r\n" +
                        "Max-Forwards: 70\r\n" +
                        "To: <sip:ss2003510ss5003@phone1.icsoc.net:5091>\r\n" +
                        "From: <sip:ss2003510ss5003@phone1.icsoc.net:5091>;tag=7thG0bNI\r\n" +
                        "Call-ID: xfMPVYgc-1711638279511@yinchaodeMacBook-Pro-2.local\r\n" +
                        "CSeq: 1 REGISTER\r\n" +
                        "Expires: 3600\r\n" +
                        "Contact: <sip:ss2003510ss5003@192.168.1.9:52770;transport=UDP\r\n"+
                        "\r\n";*/
        String msg = "REGISTER sip:rtc.icsoc.net SIP/2.0\r\n" +
                "Via: SIP/2.0/WSS 149q4i0rvb0k.invalid;branch=z9hG4bK1289867\r\n" +
                "Max-Forwards: 69\r\n" +
                "To: <sip:ss2003510ss5006@rtc.icsoc.net>\r\n" +
                "From: <sip:ss2003510ss5006@rtc.icsoc.net>;tag=c4oc9fmvsp\r\n" +
                "Call-ID: jbv3vv7geg381h5m7bm0d2\r\n" +
                "CSeq: 1 REGISTER\r\n" +
                "Contact: <sip:baneajd0@149q4i0rvb0k.invalid;transport=ws>;+sip.ice;reg-id=1;+sip.instance=\"<urn:uuid:141b9d29-815b-4273-a720-8cdf188280eb>\";expires=600\r\n" +
                "Expires: 600\r\n" +
                "Allow: INVITE,ACK,CANCEL,BYE,UPDATE,MESSAGE,OPTIONS,REFER,INFO,NOTIFY\r\n" +
                "Supported: path,gruu,outbound\r\n" +
                "User-Agent: JsSIP 3.10.1\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .handler(new MyChannelInitializer());
            Channel ch = b.bind(7398).sync().channel();
            //向目标端口发送信息
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(msg, Charset.forName("GBK")),
                    new InetSocketAddress("phone1.icsoc.net", 5091))).sync();
            ch.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
