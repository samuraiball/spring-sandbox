package dev.hirooka.rsocketserver.handler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestConstructor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HelloHandlerTest {

    private static RSocketRequester requester;

    HelloHandlerTest(RSocketRequester.Builder builder, @LocalRSocketServerPort int port) {
        requester = builder.connectTcp("localhost", port).block();
    }

    @Test
    void hello() {
        final Mono<String> response = requester
                .route("hello")
                .data("message")
                .retrieveMono(String.class);

        StepVerifier.create(response)
                .expectNext("Hello message!!")
                .expectComplete()
                .verify();
    }
}