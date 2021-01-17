package dev.hirooka.rsocket.fireandforget.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RsocketFireAndForgetClientApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "client");
        SpringApplication.run(RsocketFireAndForgetClientApplication.class, args);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
