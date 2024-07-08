package extractor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    public void insertData() {
        MyData data1 = new MyData();
        data1.setField1("value1");
        data1.setField2("value2");

        MyData data2 = new MyData();
        data2.setField1("value3");
        data2.setField2("value4");

        // Add more data as needed

        dataRepository.saveAll(Arrays.asList(data1, data2));
    }
}
