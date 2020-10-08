package hirooka.dev.errorhandlingwithrouterfunction;

import hirooka.dev.errorhandlingwithrouterfunction.exception.InternalServerErrorException;
import hirooka.dev.errorhandlingwithrouterfunction.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ExampleResource {

    public Mono<ServerResponse> throwExceptionWhileSayHello(ServerRequest serverRequest) {
        return sayHello()
                .flatMap(s -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(s, String.class))
                .onErrorResume(ResourceNotFoundException.class, e -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> throwUnexpectedException(ServerRequest serverRequest) {
        return throwRuntimeException()
                .flatMap(s -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(s, String.class))
                .onErrorResume(RuntimeException.class, e -> Mono.error(new InternalServerErrorException("something happened")));
    }

    public Mono<String> throwRuntimeException() {
        return Mono.error(new RuntimeException("something happened"));
    }

    private Mono<String> sayHello() {
        return Mono.error(new ResourceNotFoundException("Example Resource Not Found"));
    }
}
