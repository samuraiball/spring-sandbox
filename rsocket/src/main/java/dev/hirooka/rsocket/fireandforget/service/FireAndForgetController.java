package dev.hirooka.rsocket.fireandforget.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class FireAndForgetController {
    private final Logger logger = LoggerFactory.getLogger(FireAndForgetController.class);

    @MessageMapping("/greeting")
    void greet(@Headers Map<String, Object> headers, @Payload String command) {
        headers.forEach((key, value) -> logger.info(key + '=' + value));
        logger.info("new command sent from client: " + command + ".");
    }
}
