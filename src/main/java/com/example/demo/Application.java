package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @GetMapping("/")
    public String home() {
        return "DevOps pipeline on AWS is working!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
