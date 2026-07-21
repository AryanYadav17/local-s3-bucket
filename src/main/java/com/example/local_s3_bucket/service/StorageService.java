package com.example.local_s3_bucket.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    String upload(MultipartFile file);

    Resource download(String fileName);

    List<String> listFiles();

    void delete(String fileName);

    void rename(String oldName, String newName);
}