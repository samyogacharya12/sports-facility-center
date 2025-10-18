package org.sports.facility.center.multipartfile;

import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.ImageDto;
import org.sports.facility.center.service.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/images")
@Slf4j
public class ImageUploadController {
    @Autowired
    private GoogleCloudStorageService gcsService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = gcsService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            log.error("Error while uploading images ", e);
            return ResponseEntity.internalServerError().body("Error uploading image: " + e.getMessage());
        }
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestBody ImageDto imageDto) {
        try {
            byte[] content = gcsService.downloadFile(imageDto.getFileName());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageDto.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
        } catch (Exception e) {
            log.error("Error while uploading images ", e);
            return ResponseEntity.ok().contentType(MediaType.ALL).body(new byte[0]);
        }
    }



}
