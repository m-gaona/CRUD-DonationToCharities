package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {
    Image uploadImage(MultipartFile multipartFile, UUID uuid) throws Exception;
    Image getImage(UUID uuid) throws Exception;
    ResponseEntity<?> downloadImage(UUID uuid) throws Exception;
    void updateImage(MultipartFile multipartFile, UUID uuid) throws Exception;
    Image deleteImage(UUID imageUUID, UUID entityUUID) throws Exception;
}
