# Mini S3 Bucket

A Spring Boot application that simulates the core functionality of Amazon S3 using the local file system. The project is designed with clean architecture, service abstraction, and metadata management, making it easy to replace local storage with cloud providers such as AWS S3 or MinIO.

---

## 🚀 Features

- Upload files
- Download files
- List stored objects
- Delete objects
- UUID-based object keys
- JSON-based metadata persistence
- Global exception handling
- Service abstraction using interfaces
- RESTful APIs

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3
- Maven
- Jackson
- Java NIO
- REST APIs

---

## 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.example.local_s3_bucket
    │       ├── config
    │       ├── controller
    │       ├── exception
    │       ├── model
    │       └── service
    └── resources
```

---

## 📌 Implemented APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/storage/upload` | Upload file |
| GET | `/storage/download/{objectKey}` | Download file |
| GET | `/storage/files` | List object keys |
| GET | `/storage/metadata` | List metadata |
| GET | `/storage/metadata/{objectKey}` | Get metadata |
| DELETE | `/storage/{objectKey}` | Delete file |

---

## 📁 Metadata Stored

Each uploaded object stores:

- Object Key (UUID)
- Original File Name
- File Size
- Content Type
- Upload Timestamp

---

## 🔮 Future Enhancements

- Bucket Support
- MySQL Metadata Storage
- AWS S3 Integration
- MinIO Support
- File Versioning
- Multipart Upload
- Presigned URLs
- Object Search

---

## 👨‍💻 Author

Aryan Yadav