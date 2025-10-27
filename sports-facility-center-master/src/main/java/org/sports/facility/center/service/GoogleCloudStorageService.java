package org.sports.facility.center.service;

import org.springframework.web.multipart.MultipartFile;


public interface GoogleCloudStorageService {

    FacilityDto uploadImage(FacilityDto facilityDto, MultipartFile file);

    byte[] downloadFile(String objectName);

}
