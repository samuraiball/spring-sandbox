package dev.hirooka.rsocketserver.handler;

import dev.hirooka.rsocketserver.common.ClientRequesterRegister;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class HelloHandler {

    ClientRequesterRegister requesterRegister;

    public HelloHandler(ClientRequesterRegister requesterRegister){
        this.requesterRegister = requesterRegister;
    }

    @MessageMapping("hello")
    public Mono<String> hello(Mono<String> request) {
        return request.map(s -> String.format("Hello %s!!", s));
    }

    @MessageMapping("hello.requester")
    @SendTo("/hello")
    public void sendMessage(RSocketRequester requester) {
    }

}
