package com.sip.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yinchao
 * @ClassName: com.sip.proxy.WebApplication
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 16:08
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.sip.proxy",
    "com.sip.proxy.controller",
    "com.sip.proxy.config"
})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
