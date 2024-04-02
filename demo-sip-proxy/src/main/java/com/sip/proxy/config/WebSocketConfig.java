package com.sip.proxy.config;

import com.sip.proxy.WebApplication;
import com.sip.proxy.controller.EchoChannel;
import com.sip.proxy.interceptor.WsHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @author: yinchao
 * @ClassName: WebSocketConfig
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 16:10
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Resource
    private EchoChannel wsHandleController;

    @Resource
    private WsHandShakeInterceptor wsHandShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandleController, "/call")
                .addInterceptors(wsHandShakeInterceptor).setAllowedOrigins("*");
    }
}
