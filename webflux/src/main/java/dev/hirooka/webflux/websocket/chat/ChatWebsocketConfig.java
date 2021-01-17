package dev.hirooka.webflux.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class ChatWebsocketConfig {

    public ChatWebsocketConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    private final Map<String, Connection> sessions = new ConcurrentHashMap<>();

    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();


    @Bean
   HandlerMapping chatHm(){
        return new SimpleUrlHandlerMapping(){
            {
                this.setUrlMap(Map.of("/ws/chat", chatWsh()));
                this.setOrder(2);
            }
        };
    }

    @Bean
    WebSocketHandler chatWsh() {

        Flux<Message> messageFlux = Flux.<Message>create(sink -> {
            var submit = Executors.newSingleThreadExecutor().submit(() -> {
                while (true) {
                    try {
                        sink.next(this.messages.take());
                    } catch (InterruptedException e) {
                        sink.error(new RuntimeException(e));
                    }
                }
            });
            sink.onCancel(() -> submit.cancel(true));
        }).share();

        return session -> {
            String id = session.getId();
            this.sessions.put(id, new Connection(id, session));

            Flux<Boolean> in = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(this::messageFromJson)
                    .map(msg -> new Message(id, msg.getText(), new Date()))
                    .map(this.messages::offer)
                    .doFinally(st -> {
                        if (st.equals(SignalType.ON_COMPLETE)) {
                            this.sessions.remove(id);
                        }
                    });

            Flux<WebSocketMessage> out = messageFlux
                    .map(this::jsonFromMessage)
                    .map(session::textMessage);

            return session.send(out).and(in);
        };
    }


    private Message messageFromJson(String json) {
        try {
            return this.objectMapper.readValue(json, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String jsonFromMessage(Message msg) {
        try {
            return this.objectMapper.writeValueAsString(msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
