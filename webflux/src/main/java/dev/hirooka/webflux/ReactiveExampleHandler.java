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
public class ReactiveExampleHandler {

    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("hello"));
    }

    public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.just(new Employee("henoheno"), new Employee("moheji")), Employee.class);
    }

//    Stream<Integer> stream = Stream.iterate(0, i -> i + 1); // Java8の無限Stream
//    Flux<Map<String, Integer>> value = Flux.fromStream(stream.limit(10))
//            .map(i -> Collections.singletonMap("value", i));

    public Mono<ServerResponse> getIntervalNum(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(Flux.interval(Duration.ofSeconds(3)).take(3), Long.class);
    }
}
