package dev.hirooka.application;

import dev.hirooka.library.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "dev.hirooka")
@RestController
public class Application {


    private MyService myService;

    public Application(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/")
    public String hoge(){
        return myService.message();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
