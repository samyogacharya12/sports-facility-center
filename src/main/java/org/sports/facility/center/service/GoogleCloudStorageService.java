package org.sports.facility.center.service;

import org.sports.facility.center.dto.FacilityDto;
import org.springframework.web.multipart.MultipartFile;


public interface GoogleCloudStorageService {

    FacilityDto uploadImage(FacilityDto facilityDto);

    byte[] downloadFile(String objectName);

}
