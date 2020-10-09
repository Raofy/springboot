package com.ryan.fuyispringbootstarter.service;

import com.ryan.fuyispringbootstarter.HelloProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    HelloProperties helloProperties;

    public HelloProperties getHelloProperties() {
        return helloProperties;
    }

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHello(String name) {
        return helloProperties.getPrefix() + name + helloProperties.getSuffix();


    }
}
