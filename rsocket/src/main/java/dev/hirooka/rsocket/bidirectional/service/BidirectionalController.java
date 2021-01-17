package dev.hirooka.rsocket.bidirectional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Controller
public class BidirectionalController {
    private final Logger logger = LoggerFactory.getLogger(BidirectionalController.class);

    @MessageMapping("/greeting")
    Flux<String> greet(RSocketRequester client, @Payload String command) {
        Flux<String> health = client.route("health")
                .data(Mono.empty())
                .retrieveFlux(String.class)
                .doOnNext(logger::info);

        Flux<String> replayFlux = Flux.fromStream(Stream.generate(() -> "hello"))
                .delayElements(Duration.ofSeconds(1));

        return replayFlux
                .takeUntilOther(health)
                .doOnNext(logger::info);
    }
}
