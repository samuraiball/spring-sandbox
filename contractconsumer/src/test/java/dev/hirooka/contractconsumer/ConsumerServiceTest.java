package dev.hirooka.contractconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "dev.hirooka:contractproducer:+:stubs:8081"
)
public class ConsumerServiceTest {


    @Autowired
    private ConsumerService consumerService;

    @Test
    void consumerTest() {
        Hello hello = consumerService.getHello();
        assertThat(hello.getHello()).isEqualTo("world");
    }
}
