package dev.hirooka.webflux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ExampleHandler {

    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("hello"));
    }

    public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.just(new Employee("henoheno"), new Employee("moheji")), Employee.class);
    }


    public Mono<ServerResponse> getIntervalNum(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.interval(Duration.ofSeconds(3)).take(3), Employee.class);
    }
}
