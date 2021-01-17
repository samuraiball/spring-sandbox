package dev.hirooka.rsocket.bidirectional.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RsocketBidirectionalClientApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "client");
        SpringApplication.run(RsocketBidirectionalClientApplication.class, args);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
