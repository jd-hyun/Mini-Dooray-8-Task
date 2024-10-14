package com.nhnacademy.minidooray8task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MiniDooray8TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniDooray8TaskApplication.class, args);
    }

}
