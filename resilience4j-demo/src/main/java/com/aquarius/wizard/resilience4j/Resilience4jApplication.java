package com.aquarius.wizard.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhaoyijie
 * @since 2024/7/29 10:52
 */
@SpringBootApplication(scanBasePackages = {"com.aquarius.wizard.resilience4j"})
public class Resilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jApplication.class, args);
    }
}
