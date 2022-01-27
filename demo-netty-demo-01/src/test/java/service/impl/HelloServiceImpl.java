package service.impl;

import service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public void echo() {
        System.out.println("hi itstack demo rpc");
    }
}
