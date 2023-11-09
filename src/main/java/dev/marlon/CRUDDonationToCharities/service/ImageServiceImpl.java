package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.model.Charity;
import dev.marlon.CRUDDonationToCharities.model.Donor;
import dev.marlon.CRUDDonationToCharities.model.Image;
import dev.marlon.CRUDDonationToCharities.repository.CharityRepository;
import dev.marlon.CRUDDonationToCharities.repository.DonorRepository;
import dev.marlon.CRUDDonationToCharities.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final CharityRepository charityRepository;
    private final DonorRepository donorRepository;
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CharityRepository charityRepository, DonorRepository donorRepository) {
        this.imageRepository = imageRepository;
        this.charityRepository = charityRepository;
        this.donorRepository = donorRepository;
    }

    @Override
    public Image uploadImage(MultipartFile multipartFile, UUID uuid) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new Exception("Uploaded file is empty");
        }
        Image uploadedImage = new Image();
        uploadedImage.setFileName(multipartFile.getOriginalFilename());
        uploadedImage.setImageData(multipartFile.getBytes());
        uploadedImage.setContentType(multipartFile.getContentType());

        Optional<Charity> charityOptional = charityRepository.findById(uuid);
        if (charityOptional.isPresent()) {
            Charity charity = charityOptional.get();
            charity.setImage(uploadedImage);
            return imageRepository.save(uploadedImage);
        }

        Optional<Donor> donorOptional = donorRepository.findById(uuid);
        if (donorOptional.isPresent()) {
            Donor donor = donorOptional.get();
            donor.setImage(uploadedImage);
            return imageRepository.save(uploadedImage);
        }

        throw new Exception("No Charity or Donor found for the provided UUID");
    }

    @Override
    public Image getImage(UUID uuid) throws Exception {
        Image image = imageRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Image not found"));
        return image;
    }

    @Override
    public ResponseEntity<?> downloadImage(UUID uuid) throws Exception {
        Image image = imageRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Image not found"));
        byte[] imageData = image.getImageData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getContentType()));

        headers.setContentDispositionFormData("attachment", image.getFileName()); // Change the filename accordingly

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @Override
    public void updateImage(MultipartFile multipartFile, UUID uuid) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new Exception("Uploaded file is empty");
        }

        Image imageToUpdate = imageRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Image not found"));
        imageToUpdate.setImageData(multipartFile.getBytes());
        imageToUpdate.setFileName(multipartFile.getOriginalFilename());
        imageToUpdate.setContentType(multipartFile.getContentType());

        imageRepository.save(imageToUpdate);
    }

    @Override
    public Image deleteImage(UUID imageUUID, UUID entityUUID) throws Exception {
        Image image = imageRepository.findById(imageUUID)
                .orElseThrow(() -> new Exception("Image not found"));

        Optional<Charity> charityOptional = charityRepository.findById(entityUUID);
        if (charityOptional.isPresent()) {
            Charity charity = charityOptional.get();
            charity.setImage(null);
            charityRepository.save(charity);
            imageRepository.delete(image);
            return image;
        }

        Optional<Donor> donorOptional = donorRepository.findById(entityUUID);
        if (donorOptional.isPresent()) {
            Donor donor = donorOptional.get();
            donor.setImage(null);
            donorRepository.save(donor);
            imageRepository.delete(image);
            return image;
        }

        throw new Exception("Entity not found or not associated with the specified image.");
    }
}
