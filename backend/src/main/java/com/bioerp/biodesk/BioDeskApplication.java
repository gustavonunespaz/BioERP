package com.bioerp.biodesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BioDeskApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioDeskApplication.class, args);
    }
}
