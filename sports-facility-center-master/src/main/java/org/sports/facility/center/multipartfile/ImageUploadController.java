package org.sports.facility.center.multipartfile;

import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.ImageDto;
import org.sports.facility.center.service.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sports.facilities.dto.*;

@Controller
@Slf4j
public class ImageUploadController {
    @Autowired
    private GoogleCloudStorageService gcsService;

    @PostMapping("api/facility")
    public ResponseEntity<String> uploadImage(FacilityDto facilityDto,@RequestParam("file") MultipartFile file) {
        try {
            FacilityDto facilityDto = gcsService.uploadImage(facilityDto,file);
            return ResponseEntity.ok(facilityDto);
        } catch (Exception e) {
            log.error("Error while uploading facility ", e);
            return new ResponseEntity(null, HttpStatus.ok);
        }
    }

    @GetMapping("/images/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam(value = "imageName") String imageName) {
        try {
            byte[] content = gcsService.downloadFile(imageName);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
        } catch (Exception e) {
            log.error("Error while uploading images ", e);
            return ResponseEntity.ok().contentType(MediaType.ALL).body(new byte[0]);
        }
    }



}
