package dev.hirooka.routerfunctionfilter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloResource {

    public Mono<ServerResponse> greeting(ServerRequest request){
        String name = request.queryParam("name").orElse("ヒロオカ");
        return ServerResponse.ok().body(Mono.just(String.format("Hello, %s", name)), String.class);
    }


    public Mono<ServerResponse> notFiltered(ServerRequest request){
        String name = request.queryParam("name").orElse("ヒロオカ");
        return ServerResponse.ok().body(Mono.just(String.format("Hello, %s", name)), String.class);
    }
}
