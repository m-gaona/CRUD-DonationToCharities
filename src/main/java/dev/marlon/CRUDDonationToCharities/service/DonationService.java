package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.DonationRequest;
import dev.marlon.CRUDDonationToCharities.dto.DonationResponse;
import dev.marlon.CRUDDonationToCharities.model.Donation;

import java.util.List;

public interface DonationService {
    List<DonationResponse> getAllDonations() throws Exception;
    DonationResponse viewDonation(String referenceNumber) throws Exception;
    DonationResponse sendDonation(DonationRequest request) throws Exception;
    void updateDonation(DonationRequest request, String referenceNumber) throws Exception;
    DonationResponse cancelDonation(String referenceNumber) throws Exception;

}
