package demo.netty.network.test;

import com.alibaba.fastjson.JSON;
import demo.netty.network.client.NettyClient;
import demo.netty.network.future.SyncWrite;
import demo.netty.network.msg.Request;
import demo.netty.network.msg.Response;
import io.netty.channel.ChannelFuture;

public class ClientStart {

    private static ChannelFuture channelFuture;

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        new Thread(client).start();

        while (true) {
            if (channelFuture == null) {
                channelFuture = client.getChannelFuture();
                Thread.sleep(300);
                continue;
            }

            Request request = new Request();
            SyncWrite s = new SyncWrite();
            Response response = s.writeAndSync(channelFuture.channel(), request, 100);
            System.out.println("调用结果：" + JSON.toJSON(response));
            Thread.sleep(1000);
        }

    }
}
