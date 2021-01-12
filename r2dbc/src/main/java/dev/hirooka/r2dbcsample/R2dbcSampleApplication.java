package dev.hirooka.r2dbcsample;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class R2dbcSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcSampleApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> setUpEndPoints(CustomerHandler customerHandler) {
        return route()
                .nest(path("/customers"),
                        builder -> builder
                                .GET("/{id}", customerHandler::find)
                                .GET(customerHandler::findAll)
                                .POST(customerHandler::create)
                                .filter((req, res) -> res.handle(req)
                                        .onErrorResume(CustomerNotFoundException.class,
                                                e -> ServerResponse.notFound().build()))).build();
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("./db-schema.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}
