package dev.hirooka.webflux;

import dev.hirooka.webflux.http.ReactiveExampleHandler;
import dev.hirooka.webflux.websocket.EchoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> route(ReactiveExampleHandler reactiveExampleHandler) {
        return RouterFunctions.route(GET("employees"), reactiveExampleHandler::getEmployee)
                .andRoute(GET("/intervalNum"), reactiveExampleHandler::getIntervalNum)
                .andRoute(GET("/hello"), reactiveExampleHandler::hello);
    }

    @Bean
    public HandlerMapping handlerMapping(EchoHandler handler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/echo", handler);
        return new SimpleUrlHandlerMapping(map, 10);
    }


    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
