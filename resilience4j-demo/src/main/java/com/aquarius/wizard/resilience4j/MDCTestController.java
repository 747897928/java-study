package com.aquarius.wizard.resilience4j;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaoyijie
 * @since 2024/7/29 17:02
 */
@Slf4j
@RestController
public class MDCTestController {

    @GetMapping("/do1")
    public ResponseEntity<String> do1() {
        log.info("The traceId is {}", MDC.get("traceId"));
        HttpHeaders headers = new HttpHeaders();
        if (MDC.get("traceId") != null) {
            headers.add("traceId", MDC.get("traceId"));
        }
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange("http://localhost:8000/do2",
                HttpMethod.GET,
                httpEntity,
                String.class);
        log.info("The response is {}", response);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/do2")
    public ResponseEntity<String> do2() {
        log.info("The traceId is {}", MDC.get("traceId"));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
