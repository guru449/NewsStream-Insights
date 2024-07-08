package extractor.controller;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<MyData, String> {
    // Custom query methods can be defined here
}