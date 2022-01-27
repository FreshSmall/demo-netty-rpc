package demo.netty.config.spring.bean;

import demo.netty.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProviderBean extends ProviderConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 发布生成者
        doExport();
    }
}
