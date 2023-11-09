package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.CharityDTO;
import dev.marlon.CRUDDonationToCharities.dto.FundSummaryDTO;
import dev.marlon.CRUDDonationToCharities.model.Charity;

import java.util.List;
import java.util.UUID;

public interface CharityService {
    Charity createCharity(CharityDTO charityDTO) throws Exception;

    List<Charity> getAllCharities() throws Exception;

    Charity getCharity(UUID uuid) throws Exception;

    void updateCharity(UUID uuid, CharityDTO charityDTO) throws Exception;

    Charity deleteCharity(UUID uuid) throws Exception;
    FundSummaryDTO getCharitableFunds(UUID uuid) throws Exception;
}
