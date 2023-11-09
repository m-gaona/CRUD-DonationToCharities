package dev.marlon.CRUDDonationToCharities.controller;

import dev.marlon.CRUDDonationToCharities.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/media")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload",
                consumes = {
                    "multipart/form-data"}
    )
    public ResponseEntity<?> uploadImage(@RequestParam(value = "file")MultipartFile multipartFile,
                                         @RequestParam(value = "entityID") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(imageService.uploadImage(multipartFile, uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/view")
    public ResponseEntity<?> getImage(@RequestParam("id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.getImage(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/download")
    public ResponseEntity<?> downloadImage(@RequestParam("id") UUID uuid) {
        try {
            return imageService.downloadImage(uuid);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update",
                consumes = {
                    "multipart/form-data"}
    )
    public ResponseEntity<?> updateImage(@RequestParam(value = "file")MultipartFile multipartFile,
                                         @RequestParam("id") UUID uuid) {
        try {
            imageService.updateImage(multipartFile, uuid);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteImage(@RequestParam("imageID") UUID imageUUID,
                                         @RequestParam("entityID") UUID entityUUID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImage(imageUUID,entityUUID));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
