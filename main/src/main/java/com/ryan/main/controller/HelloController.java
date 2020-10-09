package com.ryan.main.controller;

import com.ryan.fuyispringbootstarter.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class HelloController {


    @Resource
    HelloService helloService;

    @RequestMapping("/hello")
    public String hello(){
        return helloService.sayHello("fuyi");
    }

}
