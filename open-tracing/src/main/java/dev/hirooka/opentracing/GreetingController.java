package dev.hirooka.opentracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class GreetingController {


    public GreetingController(RestTemplate client) {
        this.client = client;
    }

    private RestTemplate client;

    private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/hello")
    String greeting(@RequestHeader Map<String, String> header) {
        logger.info(header.toString());
        // return client.getForEntity("http://localhost:8083/hello", String.class).getBody();
        return "Hello, Tracing";
    }
}
