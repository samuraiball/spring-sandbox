package dev.hirooka.contractproducer;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

public class ContractTestBase {

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new HelloController());
    }
}
