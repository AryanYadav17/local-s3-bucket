package com.example.local_s3_bucket.service;

import com.example.local_s3_bucket.model.StorageObjectMetadata;
import java.util.List;

public interface MetadataService {

    void save(StorageObjectMetadata metadata);

    List<StorageObjectMetadata> findAll();

    StorageObjectMetadata findByObjectKey(String objectKey);

    void delete(String objectKey);

}