package dev.hirooka.routerfunctionfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class RouterFunctionFilterApplication {

    private final HelloResource helloResource;
    private final Logger logger = LoggerFactory.getLogger(RouterFunctionFilterApplication.class);

    public RouterFunctionFilterApplication(HelloResource helloResource) {
        this.helloResource = helloResource;
    }

    public static void main(String[] args) {
        SpringApplication.run(RouterFunctionFilterApplication.class, args);
    }

    @Bean
    public RouterFunction router() {
        return RouterFunctions.route(GET("/hello"), helloResource::greeting)
                .filter(new RequestLoggingFilter());
//                .filter((r, n)->{
//                    logger.info("filtered 1");
//                    return n.handle(r);
//                }).andRoute(GET("/notfiltered"),helloResource::notFiltered);

    }

}
