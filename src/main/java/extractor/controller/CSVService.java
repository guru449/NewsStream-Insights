package extractor.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class CSVService {

    private static final int CHUNK_SIZE = 50000;

    @Autowired
    private MongoDatabase mongoDatabase;

    public void generateCSV(String baseFilename) throws IOException {
        MongoCollection<Document> collection = mongoDatabase.getCollection("mydata");
        long totalRecords = collection.countDocuments();
        int fileIndex = 0;
        int offset = 0;

        while (offset < totalRecords) {
            MongoCursor<Document> cursor = collection.find().skip(offset).limit(CHUNK_SIZE).iterator();
            String filename = baseFilename + "_" + fileIndex + ".csv";
            try (FileWriter out = new FileWriter(filename);
                 CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("Field1", "Field2"))) {

                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String field1 = doc.getString("field1");
                    String field2 = doc.getString("field2");
                    // Add more fields as necessary

                    printer.printRecord(field1, field2);
                }
            }

            offset += CHUNK_SIZE;
            fileIndex++;
        }
    }
}
