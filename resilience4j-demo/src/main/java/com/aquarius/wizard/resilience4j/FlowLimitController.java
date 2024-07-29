package com.aquarius.wizard.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author zhaoyijie
 * @since 2024/7/29 11:39
 */
@RestController
@Slf4j
public class FlowLimitController {

    public ResponseEntity fallback(Throwable throwable) {
        log.error("fallback call received", throwable);
        return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/retry")
    @Retry(name = "retryApi", fallbackMethod = "fallback")
    public ResponseEntity<String> retryApi() {
        log.info("{}", "retryApi call received");
        String ret = new RestTemplate().getForObject("http://localhost:9999/", String.class);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/circuitbreaker")
    @CircuitBreaker(name = "circuitBreakerApi", fallbackMethod = "fallback")
    public ResponseEntity circuitBreakerApi() {
        log.info("{}", "circuitBreakerApi call received");
        int r = new Random().nextInt(100);
        if (r >= 70) {
            throw new RuntimeException("Unexcepted Exception");
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping("/flowlimit")
    @RateLimiter(name = "flowlimitApi", fallbackMethod = "fallback")
    public ResponseEntity flowlimitApi() {
        log.info("{}", "flowlimitApi call received");
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
