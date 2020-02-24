package dev.hirooka.spring.security.rsocket.sample.rsocketsecutirysample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
public class RsocketSecuritySampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketSecuritySampleApplication.class, args);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingResponse {
    private String message;
}

@Controller
class GreetingController {

    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(@AuthenticationPrincipal Mono<UserDetails> user) {
        return user.map(UserDetails::getUsername).flatMapMany(GreetingController::greet);
    }

    private static Flux<GreetingResponse> greet(String name) {
        return Flux.fromStream(Stream.generate(() -> new GreetingResponse("Hello, " + name)))
                .delayElements(Duration.ofSeconds(1));
    }
}




