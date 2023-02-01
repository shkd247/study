package com.example.toby_spring_reactive.week6.lecture12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {
    @RestController
    public static class MyController {
        @GetMapping("/service")
        public String service(String req) throws InterruptedException {
            Thread.sleep(1000);
            return req + "/service1"; // html/text
        }

        @GetMapping("/service2")
        public String service2(String req) throws InterruptedException {
            Thread.sleep(1000);
            return req + "/service2"; // html/text
        }
    }


    public static void main(String[] args) {
        System.setProperty("server.port","8081");
        System.setProperty("server.tomcat.threads.max","1000");

        SpringApplication.run(RemoteService.class, args);
    }

}