package com.amirkenesbay.controllers;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @GetMapping("/v2/hello")
    public ResponseEntity<String> helloV2() {
        Span newSpan = tracer.nextSpan();

        newSpan.tag("Hello", "test");
        ResponseEntity<String> response = restTemplate.postForEntity("https://httpbin.org/post", "Hello, Cloud!", String.class);
        log.info("Response: " + response);

        newSpan.end();
        return response;
    }
}