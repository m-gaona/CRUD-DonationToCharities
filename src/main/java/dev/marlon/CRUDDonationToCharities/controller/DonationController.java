package dev.marlon.CRUDDonationToCharities.controller;

import dev.marlon.CRUDDonationToCharities.dto.DonationRequest;
import dev.marlon.CRUDDonationToCharities.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/donate")
public class DonationController {
    private final DonationService donationService;

    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping(value = "/send-donation")
    public ResponseEntity<?> sendDonation(@RequestBody DonationRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(donationService.sendDonation(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-donations")
    public ResponseEntity<?> getDonations() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donationService.getAllDonations());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/view-donation")
    public ResponseEntity<?> viewDonation(@RequestParam(value = "referenceNumber") String referenceNumber) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donationService.viewDonation(referenceNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateDonation(@RequestBody DonationRequest request,
                                            @RequestParam(value = "referenceNumber") String referenceNumber) {
        try {
            donationService.updateDonation(request, referenceNumber);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/cancel")
    public ResponseEntity<?> cancelDonation(@RequestParam(value = "referenceNumber") String referenceNumber) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(donationService.cancelDonation(referenceNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
