package com.example.local_s3_bucket.service;

import com.example.local_s3_bucket.config.StorageProperties;
import com.example.local_s3_bucket.exception.StorageFileNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.example.local_s3_bucket.model.StorageObjectMetadata;

import java.time.LocalDateTime;

@Service
public class localStorageService implements StorageService {

    private final StorageProperties storageProperties;
    private final MetadataService metadataService;

    public localStorageService(StorageProperties storageProperties,
                               MetadataService metadataService) {

        this.storageProperties = storageProperties;
        this.metadataService = metadataService;
    }
    @PostConstruct
    public void init() throws IOException {

        Path path = Paths.get(storageProperties.getLocation());

        Files.createDirectories(path);
    }

    @Override
    public String upload(MultipartFile file) {

        try {

            String fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();

            String extension = "";

            if (fileName != null && fileName.contains(".")) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            }

            String objectKey = uuid + extension;

            Path destination =
                    Paths.get(storageProperties.getLocation(), objectKey);

            Files.copy(file.getInputStream(), destination);

            StorageObjectMetadata metadata = new StorageObjectMetadata(
                    objectKey,
                    fileName,
                    file.getSize(),
                    file.getContentType(),
                    LocalDateTime.now()
            );

            metadataService.save(metadata);

            return objectKey;

        } catch (IOException e) {

            throw new RuntimeException("Failed to upload file.", e);

        }

    }

    @Override
    public Resource download(String objectKey) {

        try {

            Path filePath =
                    Paths.get(storageProperties.getLocation(), objectKey);

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageFileNotFoundException(
                        "Object " + objectKey + " does not exist");
            }

            return resource;

        } catch (MalformedURLException e) {

            throw new RuntimeException("Unable to download file.", e);

        }
    }

    @Override
    public List<String> listFiles() {


        try {

            Path storagePath = Paths.get(storageProperties.getLocation());

            return Files.list(storagePath)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();

        } catch (IOException e) {

            throw new RuntimeException("Unable to list files.", e);

        }
    }

    @Override
    public void delete(String objectKey) {

        try {

            Path filePath =
                    Paths.get(storageProperties.getLocation(), objectKey);

            if (!Files.exists(filePath)) {
                throw new StorageFileNotFoundException(
                        "Object " + objectKey + " does not exist");
            }

            Files.delete(filePath);

        } catch (IOException e) {

            throw new RuntimeException("Unable to delete file.", e);

        }

    }

    @Override
    public void rename(String oldName, String newName) {

    }
}