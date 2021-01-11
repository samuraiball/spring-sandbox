package dev.hirooka.r2dbcsample;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CustomerHandler {

    private final CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Mono<ServerResponse> findAll(ServerRequest request) {
        Hooks.onOperatorDebug();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        customerRepository.findAll()
                                .map(c ->
                                        {
                                            System.out.println("---------------------------------------------------------------------------");
                                            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                                            System.out.println("---------------------------------------------------------------------------");
                                            return new CustomerDto(c.getId(), c.getUserName());
                                        }

                                ), CustomerDto.class);
    }

    Mono<ServerResponse> find(ServerRequest request) {
        String id = request.pathVariable("id");

        return customerRepository.findById(id)
                .map(c -> new CustomerDto(c.getId(), c.getUserName()))
                .flatMap(c -> ServerResponse.ok().body(Mono.just(c), CustomerDto.class))
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(String.format("customer id : %s", id))));
    }

    Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CustomerDto.class)
                .map(c -> new Customer(c.getId(), c.getName()))
                .map(customerRepository::save)
                .flatMap(customer ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .body(customer.map(c -> new CustomerDto(c.getId(), c.getUserName())), CustomerDto.class));
    }

}
