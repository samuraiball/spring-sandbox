package dev.hirooka.circuitbreaker.resilience4j.sample.resilience4jsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CBController {

    Logger LOG = LoggerFactory.getLogger(CBController.class);

    private RestTemplate restTemplate;
    private CircuitBreakerFactory circuitBreakerFactory;

    public CBController(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }


    @GetMapping("/delay/{second}")
    public Map greeting(@PathVariable int second) {
        return circuitBreakerFactory
                .create("delay")
                .run(() -> restTemplate.getForObject("https://httpbin.org/delay/" + second, Map.class),
                        t -> {
                            LOG.warn("delay call failed error", t);
                            Map<String, String> fallback = new HashMap<>();
                            fallback.put("hello", "world");
                            return fallback;
                        });
    }

}
