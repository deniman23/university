package org.example.university.generator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratedDataService {
    private final GeneratedDataRepository repository;

    @Autowired
    public GeneratedDataService(GeneratedDataRepository repository) {
        this.repository = repository;
    }

    public void saveGeneratedData(GeneratedData data) {
        repository.save(data);
    }
}
