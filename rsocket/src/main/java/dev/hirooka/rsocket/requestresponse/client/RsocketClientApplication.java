package dev.hirooka.rsocket.requestresponse.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.io.IOException;

@SpringBootApplication
public class RsocketClientApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "client");
        SpringApplication.run(RsocketClientApplication.class, args);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
