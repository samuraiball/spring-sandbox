package hirooka.dev.errorhandlingwithrouterfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class ErrorHandlingWithRouterFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingWithRouterFunctionApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> route(ExampleResource exampleResource) {
        return RouterFunctions
                .route(GET("/unexpected"), exampleResource::throwUnexpectedException);
    }
}
