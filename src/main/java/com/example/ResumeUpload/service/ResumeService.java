package com.example.ResumeUpload.service;

import com.example.ResumeUpload.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ResumeService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    S3Service s3Service;

    public String processResume(MultipartFile file) throws IOException {
        // Validate file type
        if (!isValidFileType(file)) {
            throw new IllegalArgumentException("File type must be DOCX.");
        }

        // Generate UUID
        String fileName = generateFileName(file);

        // Store file in S3 bucket
        s3Service.uploadFile(fileName, file.getInputStream());

        // Publish message to Kafka
        kafkaProducer.publishMessage(fileName);

        return "Resume uploaded successfully.";
    }

    private boolean isValidFileType(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    private String generateFileName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return uuid + "_" + LocalDate.now() + extension;
    }
}
