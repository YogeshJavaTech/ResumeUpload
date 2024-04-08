package com.example.ResumeUpload.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    @Autowired
    AmazonS3 amazonS3;

    private static final String BUCKET_NAME = "file-upload-bucket";

    public void uploadFile(String fileName, InputStream inputStream) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        amazonS3.putObject(new PutObjectRequest(BUCKET_NAME, fileName, inputStream, metadata));
    }
}
