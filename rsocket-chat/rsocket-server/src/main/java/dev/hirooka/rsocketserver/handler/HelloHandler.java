package dev.hirooka.rsocketserver.handler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class HelloHandler {

    @MessageMapping("hello")
    public Mono<String> hello(Mono<String> request){
        return request.map(s -> String.format("Hello %s!!", s));
    }

//    @MessageMapping("hello.many")
//    public Flux<String>(){
//
//    }
}
