package dev.hirooka.r2dbcsample;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    @Query("select * from customer c where c.user_name == $1")
    Mono<Customer> findByUserName(String userName);

}
