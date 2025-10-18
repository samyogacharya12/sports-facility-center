package org.sports.facility.center.service;

import org.springframework.web.multipart.MultipartFile;


public interface GoogleCloudStorageService {

    String uploadImage(MultipartFile file);

    byte[] downloadFile(String objectName);

}
