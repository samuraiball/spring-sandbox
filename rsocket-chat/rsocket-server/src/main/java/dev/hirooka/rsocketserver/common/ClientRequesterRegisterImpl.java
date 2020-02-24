package dev.hirooka.rsocketserver.common;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientRequesterRegisterImpl implements ClientRequesterRegister{

    private List<RSocketRequester> requesterList;

    @Override
    public void register(RSocketRequester requester) {
        requesterList.add(requester);
    }

    @Override
    public void subscribeMassage(String message) {
        requesterList.stream().forEach(
                requester -> {
                   requester.route("hello").data(message).retrieveMono(String.class);
                }
        );
    }
}
