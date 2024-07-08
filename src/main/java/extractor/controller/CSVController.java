package extractor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    private CSVService csvService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadCSV() throws IOException {
        String baseFilename = "data";
        csvService.generateCSV(baseFilename);

        // For simplicity, returning the first file. You may need to implement a more comprehensive download feature.
        String filename = baseFilename + "_0.csv";
        Resource file = new FileSystemResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(file);
    }
}
