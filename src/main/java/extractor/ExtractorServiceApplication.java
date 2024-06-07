package extractor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExtractorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtractorServiceApplication.class, args);
    }
}
