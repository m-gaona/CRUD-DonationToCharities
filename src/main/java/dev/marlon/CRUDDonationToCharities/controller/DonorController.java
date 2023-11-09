package dev.marlon.CRUDDonationToCharities.controller;

import dev.marlon.CRUDDonationToCharities.dto.DonorDTO;
import dev.marlon.CRUDDonationToCharities.model.Donor;
import dev.marlon.CRUDDonationToCharities.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("api/donors")
public class DonorController {
    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping(value = "/get-all-donors")
    public ResponseEntity<?> getDonors() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donorService.getAllDonors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-donor")
    public ResponseEntity<?> getDonor(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donorService.getDonor(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addDonor(@RequestBody DonorDTO donorDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(donorService.createDonor(donorDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateDonor(@RequestParam(value = "id") UUID uuid, @RequestBody DonorDTO donorDTO) {
        try {
            donorService.updateDonor(uuid,donorDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/fund-balance")
    public ResponseEntity<?> fundBalance(@RequestParam(value = "id") UUID uuid, @RequestParam BigDecimal fund) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donorService.fundBalance(uuid,fund));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteDonor(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donorService.deleteDonor(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "check-balance")
    public ResponseEntity<?> checkBalance(@RequestParam(value = "id") UUID uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donorService.checkBalance(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
