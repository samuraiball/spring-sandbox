import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FluxCreateTest {

    private final ExecutorService es = Executors.newFixedThreadPool(1);

    @Test
    void exec() {
        System.out.println("Thread Name = " + Thread.currentThread().getName());
        Flux<String> values = Flux.create(emitter -> this.execTask(emitter, 5), FluxSink.OverflowStrategy.DROP);

        StepVerifier
                .create(values.doFinally(signalType -> this.es.shutdown()))
                .expectNext("exec number: 1")
                .expectNext("exec number: 2")
                .expectNext("exec number: 3")
                .expectNext("exec number: 4")
                .expectNext("exec number: 5")
                .verifyComplete();
    }

    private void execTask(FluxSink<String> stringFluxSink, int maxNum) {
        this.es.submit(() -> {
            System.out.println("Thread Name = " + Thread.currentThread().getName());

            AtomicInteger num = new AtomicInteger();

            while (num.get() < maxNum) {
                String emitValue = "exec number: " + num.incrementAndGet();
                System.out.println("emitValue = " + emitValue);
                stringFluxSink.next(emitValue);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stringFluxSink.complete();
        });
    }


    @Test
    void testOnErrorResume() {

        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .flatMap(integer -> {
                    if (integer.equals(3)) return Flux.error(new RuntimeException("Oops"));
                    return Flux.just(integer);
                })
                .onErrorResume(e -> e.getMessage().equals("Auchi"), v -> Flux.just(10))
                .onErrorResume(e -> e.getMessage().equals("Oops"), v -> Flux.just(4));


        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(4)
                .verifyComplete();
    }


    @Test
    void testOnErrorReturn() {

        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .flatMap(integer -> {
                    if (integer.equals(3)) return Flux.error(new RuntimeException("Oops"));
                    return Flux.just(integer);
                })
                .onErrorReturn(IllegalArgumentException.class, 10)
                .onErrorReturn(RuntimeException.class, 4);

        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    void testDoOnError() {
        Hooks.onOperatorDebug();
        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .map(v -> {
                    if (v.equals(3)) throw new RuntimeException("Oops");
                    return v;
                })
                .doOnError(RuntimeException.class, v -> System.out.println("message = " + v.getMessage()));

        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectError(RuntimeException.class)
                .verify();
    }


    @Test
    void testOnErrorMap() {


        Hooks.onOperatorDebug();

        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .flatMap(integer -> {
                    if (integer.equals(3)) return Flux.error(new RuntimeException("Oops"));
                    return Flux.just(integer);
                })
                .onErrorMap(RuntimeException.class, e -> new IllegalArgumentException(e.getMessage()));

        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectError(IllegalArgumentException.class)
                .verify();
    }


    @Test
    void testOnErrorContinue() {


        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4)
                .flatMap(integer -> {
                    if (integer.equals(3)) return Flux.error(new IllegalArgumentException("Oops"));
                    return Flux.just(integer);
                })
                .onErrorContinue((e, v) -> System.out.printf("dropped value = %s", v));

        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(4)
                .verifyComplete();
    }
}
