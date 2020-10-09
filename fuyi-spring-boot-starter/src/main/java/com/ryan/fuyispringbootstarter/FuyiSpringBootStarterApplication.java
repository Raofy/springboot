package com.ryan.fuyispringbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ryan.fuyispringbootstarter.service")
public class FuyiSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuyiSpringBootStarterApplication.class, args);
    }

}
