package demo.netty.network.test;


import demo.netty.network.server.NettyServer;

public class ServerStart {

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        new Thread(server).start();
        System.out.println("netty server 已经启动");
    }
}
