package com.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {



    @GetMapping("/websocket")
    public String test(){
        return "demo";
    }
}
