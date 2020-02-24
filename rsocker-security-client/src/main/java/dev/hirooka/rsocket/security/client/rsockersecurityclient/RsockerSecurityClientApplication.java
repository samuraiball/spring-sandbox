package dev.hirooka.rsocket.security.client.rsockersecurityclient;

import io.rsocket.metadata.WellKnownMimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

@Log4j2
@SpringBootApplication
public class RsockerSecurityClientApplication {


    private final MimeType mimeType =
            MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());
    private final UsernamePasswordMetadata credentials =
            new UsernamePasswordMetadata("mohezi", "pw");

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(RsockerSecurityClientApplication.class, args);
        System.in.read();
    }

    @Bean
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies.encoder(new SimpleAuthenticationEncoder());
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder
                .setupMetadata(this.credentials, this.mimeType)
                .connectTcp("localhost", 8080)
                .block();
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(RSocketRequester requester) {
        return event -> {
            requester
                    .route("greetings")
                    .metadata(this.credentials, this.mimeType)
                    .data(Mono.empty())
                    .retrieveFlux(GreetingResponse.class)
                    .subscribe(gr -> log.info("secured response" + gr.getMessage()));
        };
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingResponse {
    private String message;
}
