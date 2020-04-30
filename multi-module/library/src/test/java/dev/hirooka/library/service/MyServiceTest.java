package dev.hirooka.library.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("service.message=Hello")
class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoad(){
        assertThat(myService.message()).isEqualTo("Hello");
    }

    @SpringBootApplication
    static class TestConfig{
    }
}