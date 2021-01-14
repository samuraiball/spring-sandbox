package dev.hirooka.webflux.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> out = Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .map(i -> i + "aaaa")
                .map(session::textMessage);


        Flux<WebSocketMessage> in = session.receive()
                .map(v -> session.textMessage(String.format("Echo: %s", v.getPayloadAsText())));


        return session.send(out).and(in);
    }
}
