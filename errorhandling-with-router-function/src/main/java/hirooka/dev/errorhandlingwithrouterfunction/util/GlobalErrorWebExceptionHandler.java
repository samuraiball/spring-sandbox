package hirooka.dev.errorhandlingwithrouterfunction.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    protected final static Logger logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);
    public GlobalErrorWebExceptionHandler(DefaultErrorAttributes g, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(g, new ResourceProperties(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), r -> {
            ErrorAttributeOptions eao = ErrorAttributeOptions.defaults();

            Map<String, Object> ea = getErrorAttributes(r,
                    eao.including(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
            );
            logger.warn("{}", ea);
            return renderJsonResponse(ea);
        });
    }

    private int getStatusCode(Map<String, Object> ea) {
        return (int) ea.get("status");
    }

    private Mono<ServerResponse> renderJsonResponse(Map<String, Object> ea) {
        ea.remove("exception");
        return ServerResponse.status(getStatusCode(ea))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ea));
    }
}
