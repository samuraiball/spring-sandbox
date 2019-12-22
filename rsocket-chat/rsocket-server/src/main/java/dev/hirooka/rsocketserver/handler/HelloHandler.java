package dev.hirooka.rsocketserver.handler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class HelloHandler {

    @MessageMapping("hello")
    public Mono<String> hello(){
        return Mono.just("hello");
    }
}
