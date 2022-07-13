package com.example.palmenttoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableKafka
public class PalmenttoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalmenttoAppApplication.class, args);
    }

}
