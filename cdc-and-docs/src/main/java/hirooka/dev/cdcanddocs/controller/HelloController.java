package hirooka.dev.cdcanddocs.controller;

import jdk.jfr.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/")
    public Map<String, String> hello() {
        return Map.of("hello", "world");
    }


    @PostMapping("/")
    public Map<String, String> hello(@RequestBody Map<String, Integer> value) {
        if (value.get("value")>=10) {
            return Map.of("status", "OK");
        }
        return Map.of("status", "NG");
    }
}
