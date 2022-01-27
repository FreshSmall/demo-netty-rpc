import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApiTest {

    public static void main(String[] args) {
        String[] configs = {"netty-rpc-consumer.xml","netty-rpc-provider.xml"};
        new ClassPathXmlApplicationContext(configs);
    }
}
