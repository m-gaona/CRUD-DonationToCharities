package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.DonorDTO;
import dev.marlon.CRUDDonationToCharities.dto.FundSummaryDTO;
import dev.marlon.CRUDDonationToCharities.model.Donor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DonorService {
    List<Donor> getAllDonors() throws Exception;
    Donor getDonor(UUID uuid) throws Exception;
    Donor createDonor(DonorDTO donorDTO) throws Exception;
    void updateDonor(UUID uuid, DonorDTO donor) throws Exception;
    Donor fundBalance(UUID uuid, BigDecimal fund) throws Exception;
    Donor deleteDonor(UUID uuid) throws Exception;
    FundSummaryDTO checkBalance(UUID uuid) throws Exception;
}
