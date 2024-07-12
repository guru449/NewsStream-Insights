package extractor.controller;

import extractor.extractor.NewsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    @CrossOrigin
    public String getTestMessage() {
        logger.error("TestController.getTestMessage");
        return "Success";
    }
}
