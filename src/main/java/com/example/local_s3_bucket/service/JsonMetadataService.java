package com.example.local_s3_bucket.service;

import com.example.local_s3_bucket.model.StorageObjectMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonMetadataService implements MetadataService {

    private static final String METADATA_FILE = "Storage/metadata.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<StorageObjectMetadata> metadataList;

    @PostConstruct
    public void init() {

        objectMapper.registerModule(new JavaTimeModule());

        File file = new File(METADATA_FILE);

        if (file.exists()) {

            try {

                metadataList = objectMapper.readValue(
                        file,
                        new TypeReference<List<StorageObjectMetadata>>() {
                        });

            } catch (IOException e) {

                metadataList = new ArrayList<>();

            }

        } else {

            metadataList = new ArrayList<>();

        }

    }

    private void saveToFile() {

        try {

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(METADATA_FILE), metadataList);

        } catch (IOException e) {

            throw new RuntimeException("Unable to save metadata.", e);

        }

    }

    @Override
    public void save(StorageObjectMetadata metadata) {

        metadataList.add(metadata);

        saveToFile();

    }

    @Override
    public List<StorageObjectMetadata> findAll() {
        return metadataList;
    }

    @Override
    public StorageObjectMetadata findByObjectKey(String objectKey) {

        return metadataList.stream()
                .filter(metadata -> metadata.getObjectKey().equals(objectKey))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(String objectKey) {

        metadataList.removeIf(metadata ->
                metadata.getObjectKey().equals(objectKey));

        saveToFile();
    }
}