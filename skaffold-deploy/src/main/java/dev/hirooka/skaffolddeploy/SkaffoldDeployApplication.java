package dev.hirooka.skaffolddeploy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@PropertySource("classpath:application.properties")
public class SkaffoldDeployApplication {

    @Value("${skaffold.env}")
    private String env;

    @GetMapping("/envval")
    public String env() {
        return env;
    }

    public static void main(String[] args) {
        SpringApplication.run(SkaffoldDeployApplication.class, args);
    }
}
