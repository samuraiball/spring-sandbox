package dev.hirooka.webflux.websocket.chat;

import java.util.Date;

public class Message {

    public Message(String clientId, String text, Date when) {
        this.clientId = clientId;
        this.text = text;
        this.when = when;
    }

    private final String clientId;
    private final String text;
    private final Date when;

    public String getClientId() {
        return clientId;
    }

    public String getText() {
        return text;
    }

    public Date getWhen() {
        return when;
    }
}
