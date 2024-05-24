package org.news;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class NewsExtractor {
    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=keyword&apiKey=1b6b2cd9f5764769a8952cf2036556ae";
    private static final String KAFKA_TOPIC = "raw-news";
    private static final String KAFKA_BROKER = "localhost:9092";

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new FetchNewsTask(), 0, 3600); // Fetch news every hour
    }

    static class FetchNewsTask extends TimerTask {
        @Override
        public void run() {
            try {
                Properties props = new Properties();
                props.put("bootstrap.servers", KAFKA_BROKER);
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

                KafkaProducer<String, String> producer = new KafkaProducer<>(props);

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
                    producer.send(new ProducerRecord<>(KAFKA_TOPIC, article.toString()));
                }
                producer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}