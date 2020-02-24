package dev.hirooka.rsocketserver.common;

import org.springframework.messaging.rsocket.RSocketRequester;


public interface ClientRequesterRegister {
    void register(RSocketRequester requester);
    void subscribeMassage(String message);
}
