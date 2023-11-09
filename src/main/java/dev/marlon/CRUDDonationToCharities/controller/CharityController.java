package dev.marlon.CRUDDonationToCharities.controller;

import dev.marlon.CRUDDonationToCharities.dto.CharityDTO;
import dev.marlon.CRUDDonationToCharities.model.Charity;
import dev.marlon.CRUDDonationToCharities.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/charities")
public class CharityController {

    private final CharityService charityService;

    @Autowired
    public CharityController(CharityService charityService) {
        this.charityService = charityService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCharity(@RequestBody CharityDTO charityDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(charityService.createCharity(charityDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding charity: " + e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-charities")
    public ResponseEntity<?> getAllCharities() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(charityService.getAllCharities());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-charity")
    public ResponseEntity<?> getCharity(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(charityService.getCharity(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-charitable-funds")
    public ResponseEntity<?> getCharitableFunds(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(charityService.getCharitableFunds(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCharity(@RequestParam(value = "id") UUID uuid,
                                        @RequestBody CharityDTO charityDTO) {
        try {
            charityService.updateCharity(uuid, charityDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCharity(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(charityService.deleteCharity(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
