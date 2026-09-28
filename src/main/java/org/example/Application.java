package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
private static Map<String, String> sessions = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
