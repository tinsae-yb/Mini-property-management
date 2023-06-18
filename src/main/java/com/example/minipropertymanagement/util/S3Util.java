package com.example.minipropertymanagement.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(byte[] imageBytes, String contentType) throws IOException {
        UUID uuid = UUID.randomUUID();
        String keyName = uuid.toString();


        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType(contentType);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        amazonS3Client.putObject(bucketName, keyName, inputStream, metadata);
        return keyName;

    }
}
