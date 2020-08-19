package dev.hirooka.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;

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
    public  WebClient webClient(WebClient.Builder builder){
        return builder.build();
    }
}
