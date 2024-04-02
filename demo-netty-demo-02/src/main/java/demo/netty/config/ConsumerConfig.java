package demo.netty.config;

public class ConsumerConfig<T> {

    // 接口
    public String nozzle;
    // 别名
    public String alias;

    // 动态代理链接
    public synchronized T refer() {
        System.out.format("消费者信息==> [接口:%s] [别名:%s] \r\n", nozzle, alias);
        return null;
    }

}
