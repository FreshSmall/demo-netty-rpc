package demo.netty.config.spring;

import demo.netty.config.spring.bean.ConsumerBean;
import demo.netty.config.spring.bean.ProviderBean;
import demo.netty.config.spring.bean.ServerBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("consumer", new MyBeanDefinitionParser(ConsumerBean.class));
        registerBeanDefinitionParser("provider", new MyBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("server", new MyBeanDefinitionParser(ServerBean.class));
    }
}
