package com.sindhuja.twitterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterServiceApplication {

    public static void main(String[] args) {
        for(String x: args) {
            System.out.println(x);
        }
        SpringApplication.run(TwitterServiceApplication.class, args);
    }

}
