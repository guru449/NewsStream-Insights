package extractor;


import extractor.extractor.NewsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class ExtractorServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(ExtractorServiceApplication.class);

    @Autowired
    private Environment environment;
    @PostConstruct
    public void logKafkaSettings() {
        logger.debug("Kafka Bootstrap Servers: " + environment.getProperty("spring.kafka.bootstrap-servers"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ExtractorServiceApplication.class, args);
    }
}
