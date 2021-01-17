package dev.hirooka.webflux.websocket.chat;

import org.springframework.web.reactive.socket.WebSocketSession;

public class Connection {

    public Connection(String id, WebSocketSession session) {
        this.id = id;
        this.session = session;
    }

    private final String id;
    private final WebSocketSession session;

    public String getId() {
        return id;
    }

    public WebSocketSession getSession() {
        return session;
    }
}
