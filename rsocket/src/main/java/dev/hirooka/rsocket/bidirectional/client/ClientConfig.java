package dev.hirooka.rsocket.bidirectional.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
public class ClientConfig {

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder){
        return builder.tcp("localhost", 8181);
    }
}
