package dev.hirooka.spring.security.rsocket.sample.rsocketsecutirysample.config;

import dev.hirooka.spring.security.rsocket.sample.rsocketsecutirysample.RsocketSecuritySampleApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Configuration
@EnableRSocketSecurity
public class SecurityConfig {

    @Bean
    RSocketMessageHandler messageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler mh = new RSocketMessageHandler();
        mh.getArgumentResolverConfigurer().addCustomResolver(new AuthenticationPrincipalArgumentResolver());
        mh.setRSocketStrategies(strategies);
        return mh;
    }

    @Bean
    MapReactiveUserDetailsService authentication() {
        UserDetails heno = User.withDefaultPasswordEncoder().username("heno").password("pw").roles("USER").build();
        UserDetails mohezi = User.withDefaultPasswordEncoder().username("mohezi").password("pw").roles("ADMIN", "USER").build();
        return new MapReactiveUserDetailsService(heno, mohezi);
    }

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {
        return security.authorizePayload(spec -> spec.route("greetings")
                .authenticated()
                .anyExchange().permitAll())
                .simpleAuthentication(Customizer.withDefaults())
                .build();
    }
}
