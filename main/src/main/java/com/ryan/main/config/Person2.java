package com.ryan.main.config;

import com.ryan.main.bean.School;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = "classpath:person.properties")
public class Person2 {

    @Value("${name}")
    private String name;
    @Value("${age}")
    private int age;
    @Value("${address}")
    private String address;
    @Value("${hobby}")
    private List hobby;

    private School school;

    public Person2() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", hobby=" + hobby +
                ", school=" + school +
                '}';
    }

    @Bean
    public void testPersionConfiguration2() {
        System.out.println(this.toString());
    }
}
