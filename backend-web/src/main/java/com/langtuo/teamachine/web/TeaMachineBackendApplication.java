package com.langtuo.teamachine.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jiaqing
 */
@SpringBootApplication
@ComponentScan(
    basePackages = {"com.langtuo.teamachine"}
)
public class TeaMachineBackendApplication {
    public static void main(String args[]) {
        SpringApplication.run(TeaMachineBackendApplication.class, args);
    }
}
