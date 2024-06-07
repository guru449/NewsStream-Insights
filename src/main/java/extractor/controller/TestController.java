package extractor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TestController {

    @GetMapping("/test")
    @CrossOrigin
    public String getTestMessage() {
        return "Success";
    }
}
