package dev.hirooka.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> route(ExampleHandler exampleHandler) {
        return RouterFunctions.route(GET("employees"), exampleHandler::getEmployee);
                //route(GET("/hello"), exampleHandler::getEmploy)
                //.andRoute(GET("/intervalNum"), exampleHandler::getIntervalNum);
    }
}
