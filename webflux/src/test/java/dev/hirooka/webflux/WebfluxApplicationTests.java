package dev.hirooka.webflux;

import dev.hirooka.webflux.http.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebfluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void hello() {
        webTestClient.get().uri("/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello");
    }

    @Test
    void getEmployee() {
        FluxExchangeResult<Employee> result = webTestClient.get().uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Employee.class);

        StepVerifier.create(result.getResponseBody())
                .expectNext()
                .expectNextMatches(employee -> employee.equals(new Employee("henoheno")))
                .expectNextMatches(employee -> employee.equals(new Employee("moheji")))
                .expectComplete()
                .verify();
    }

}
