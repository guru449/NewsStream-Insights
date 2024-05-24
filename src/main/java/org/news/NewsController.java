//package org.news;
//
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@RestController
//public class NewsController {
//
//    @Autowired
//    private KafkaProducer<String, String> producer;
//
//    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=keyword&apiKey=YOUR_API_KEY";
//    private static final String KAFKA_TOPIC = "raw-news";
//
//    @GetMapping("/fetch-news")
//    public String fetchNews() {
//        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(NEWS_API_URL).openConnection();
//            connection.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            connection.disconnect();
//
//            JSONObject jsonResponse = new JSONObject(content.toString());
//            JSONArray articles = jsonResponse.getJSONArray("articles");
//
//            for (int i = 0; i < articles.length(); i++) {
//                JSONObject article = articles.getJSONObject(i);
//                producer.send(new ProducerRecord<>(KAFKA_TOPIC, article.toString()));
//            }
//            return "News fetched and sent to Kafka";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to fetch news";
//        }
//    }
//}
