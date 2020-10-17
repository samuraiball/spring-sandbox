package dev.hirooka.routerfunctionfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class RequestLoggingFilter implements HandlerFilterFunction {

    private final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono filter(ServerRequest request, HandlerFunction next) {
        logger.info(String.format("%s, %s", request, request.queryParams()));
        return next.handle(request);
    }
}
