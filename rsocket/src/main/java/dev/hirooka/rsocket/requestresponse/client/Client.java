package dev.hirooka.rsocket.requestresponse.client;

import io.rsocket.RSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
public class Client implements ApplicationListener {

    private final RSocketRequester requester;

    private final Logger logger  = LoggerFactory.getLogger(Client.class);

    public Client(RSocketRequester requester) {
        this.requester = requester;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        logger.info("the mimeType is " + this.requester.dataMimeType());
        logger.info("the metadata mimeType is " + this.requester.metadataMimeType());
        this.requester
                .route("/greeting")
                .data("henoheno mohezi")
                .retrieveMono(String.class)
                .subscribe(System.out::println);
    }
}
