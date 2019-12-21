package dev.hirooka.rsocket.chat.rsocketchatserver.handler;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.*;

@Controller
public class ChatHandler {

    @MessageMapping("hello")
    public Mono<String> hello(){
        return just("");
    }

}
