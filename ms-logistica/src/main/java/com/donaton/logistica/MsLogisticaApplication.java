package com.donaton.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsLogisticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLogisticaApplication.class, args);
    }
}