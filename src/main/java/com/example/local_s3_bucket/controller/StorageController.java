package com.example.local_s3_bucket.controller;

import com.example.local_s3_bucket.exception.StorageFileNotFoundException;
import com.example.local_s3_bucket.service.MetadataService;
import com.example.local_s3_bucket.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.example.local_s3_bucket.model.StorageObjectMetadata;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;
    private final MetadataService metadataService;

    public StorageController(StorageService storageService,
                             MetadataService metadataService) {

        this.storageService = storageService;
        this.metadataService = metadataService;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file) {

        String uploadedFile = storageService.upload(file);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/download/{objectKey:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String objectKey) {

        System.out.println("DOWNLOAD: " + objectKey);

        Resource resource = storageService.download(objectKey);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {

        List<String> files = storageService.listFiles();

        return ResponseEntity.ok(files);

    }

    @DeleteMapping("/{objectKey}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable String objectKey) {

        storageService.delete(objectKey);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/metadata")
    public ResponseEntity<List<StorageObjectMetadata>> getAllMetadata() {

        return ResponseEntity.ok(
                metadataService.findAll()
        );

    }

    @GetMapping("/metadata/{objectKey}")
    public ResponseEntity<StorageObjectMetadata> getMetadata(
            @PathVariable String objectKey) {

        StorageObjectMetadata metadata =
                metadataService.findByObjectKey(objectKey);

        if (metadata == null) {
            throw new StorageFileNotFoundException(
                    "Object " + objectKey + " does not exist"
            );
        }

        return ResponseEntity.ok(metadata);
    }


}