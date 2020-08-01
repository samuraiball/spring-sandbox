package dev.hirooka.contractconsumer;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {
    private final RestTemplate restTemplate;

    public ConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Hello getHello(){
        ResponseEntity<Hello> helloResponse =
                restTemplate.getForEntity("http://localhost:8081/hello", Hello.class);
        return helloResponse.getBody();
    }
}


