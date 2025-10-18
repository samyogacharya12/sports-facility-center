package org.sports.facility.center.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.Storage;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {


    private final Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    public GoogleCloudStorageServiceImpl(Storage storage) {
        this.storage = storage;
    }


    @Override
    public String uploadImage(MultipartFile file) {
        try {
            log.info("uploadImage ");
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(file.getContentType())
                .build();

            storage.create(blobInfo, file.getBytes());

            // Public URL
            return String.format("https://storage.cloud.google.com/sports-facility-uploads/%s/%s", bucketName, fileName);
        } catch (Exception exception) {
            log.error("Error in uploadImage ",exception);
        }
        return null;
    }

    @Override
    public byte[] downloadFile(String objectName) {
        try {
            Blob blob = storage.get(bucketName, objectName);
            if (blob == null) {
                throw new RuntimeException("File not found: " + objectName);
            }
            log.info("content", blob.getContent());
            return blob.getContent();
        } catch (Exception exception) {
            log.error("error in downloadFile", exception);
        }
        return null;
    }
}
