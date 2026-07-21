package com.example.local_s3_bucket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageProperties {

    @Value("${storage.location}")
    private String location;

    public String getLocation() {
        return location;
    }
}