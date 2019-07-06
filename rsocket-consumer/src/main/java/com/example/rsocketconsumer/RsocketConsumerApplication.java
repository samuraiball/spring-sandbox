package com.example.rsocketconsumer;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@SpringBootApplication
public class RsocketConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsocketConsumerApplication.class, args);
	}

	@Bean
	RSocket rSocket(){
		return RSocketFactory
				.connect()
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.frameDecoder(PayloadDecoder.ZERO_COPY)
				.transport(TcpClientTransport.create(8082))
				.start()
				.block();
	}

	@Bean
	RSocketRequester requester(RSocketStrategies socketStrategies){
			return RSocketRequester.wrap(
					this.rSocket(),
					MimeTypeUtils.APPLICATION_JSON,
					MimeTypeUtils.APPLICATION_JSON,
					socketStrategies);
	}

}
