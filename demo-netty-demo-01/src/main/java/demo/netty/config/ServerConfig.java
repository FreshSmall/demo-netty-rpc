package demo.netty.config;

public class ServerConfig {
    // 注册中心地址
    public String hots;
    // 注册中心端口
    public int port;

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
