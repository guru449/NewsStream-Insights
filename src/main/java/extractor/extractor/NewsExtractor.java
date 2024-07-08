package extractor.extractor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Component
public class NewsExtractor {
    private static final Logger logger = LoggerFactory.getLogger(NewsExtractor.class);

    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=keyword&apiKey=1b6b2cd9f5764769a8952cf2036556ae";
    private static final String KAFKA_TOPIC = "raw-news";

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBroker;

    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBroker);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    @Scheduled(fixedRate = 36000)  // Execute every hour
    public void fetchNewsAndSendToKafka() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(NEWS_API_URL).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray articles = jsonResponse.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                logger.debug("Pushing news to Kafka.");
                producer.send(new ProducerRecord<>(KAFKA_TOPIC, article.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void closeProducer() {
        if (producer != null) {
            producer.close();
        }
    }
}
