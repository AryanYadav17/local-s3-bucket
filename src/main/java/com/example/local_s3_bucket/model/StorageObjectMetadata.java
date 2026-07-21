package com.example.local_s3_bucket.model;

import java.time.LocalDateTime;

public class StorageObjectMetadata {

    private String objectKey;

    private String originalFileName;

    private long size;

    private String contentType;

    private LocalDateTime uploadedAt;

    public StorageObjectMetadata() {
    }

    public StorageObjectMetadata(String objectKey,
                                 String originalFileName,
                                 long size,
                                 String contentType,
                                 LocalDateTime uploadedAt) {

        this.objectKey = objectKey;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.uploadedAt = uploadedAt;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

}