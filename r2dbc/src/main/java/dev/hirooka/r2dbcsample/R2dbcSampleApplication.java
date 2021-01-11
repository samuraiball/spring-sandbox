package dev.hirooka.r2dbcsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

@SpringBootApplication
public class R2dbcSampleApplication {

    Logger logger = LoggerFactory.getLogger(R2dbcSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(R2dbcSampleApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> setUpEndPoints(CustomerHandler customerHandler) {
        return route()
                .GET("/ping", r -> ServerResponse.ok().body("pong", String.class))
                .nest(path("/customers"),
                        builder -> builder
                                .GET("/{id}", customerHandler::find)
                                .GET(customerHandler::findAll)
                                .POST(customerHandler::create)
                                .filter((req, res) -> res.handle(req)
                                        .onErrorResume(CustomerNotFoundException.class, e -> notFound().build()))).build();
    }
}
