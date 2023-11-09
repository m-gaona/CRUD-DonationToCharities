package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.CharityDTO;
import dev.marlon.CRUDDonationToCharities.dto.FundSummaryDTO;
import dev.marlon.CRUDDonationToCharities.model.Address;
import dev.marlon.CRUDDonationToCharities.model.Charity;
import dev.marlon.CRUDDonationToCharities.repository.CharityRepository;
import dev.marlon.CRUDDonationToCharities.repository.DonationRepository;
import dev.marlon.CRUDDonationToCharities.util.CharityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CharityServiceImpl implements CharityService{
    private final CharityRepository charityRepository;
    private final DonationRepository donationRepository;
    @Autowired
    public CharityServiceImpl(CharityRepository charityRepository, DonationRepository donationRepository) {
        this.charityRepository = charityRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public Charity createCharity(CharityDTO charityDTO) throws Exception{
        Charity newCharity = new Charity();
        Charity isNamePresent = charityRepository.findByName(charityDTO.getName());
        if (isNamePresent != null) {
            throw new Exception("Charity name is already taken");
        }
        newCharity.setName(charityDTO.getName());
        newCharity.setCurrency(charityDTO.getCurrency());
        newCharity.setDescription(charityDTO.getDescription());

        Address newAddress = new Address();
        newAddress.setStreet(charityDTO.getAddress().getStreet());
        newAddress.setCity(charityDTO.getAddress().getCity());
        newAddress.setState(charityDTO.getAddress().getState());
        newAddress.setCountry(charityDTO.getAddress().getCountry());
        newAddress.setZipCode(charityDTO.getAddress().getZipCode());

        newCharity.setAddress(newAddress);

        newCharity.setDateCreated(LocalDateTime.now());
        newCharity.setStatus(CharityStatus.PENDING_APPROVAL);

        return charityRepository.save(newCharity);
    }

    @Override
    public List<Charity> getAllCharities() throws Exception {
        List<Charity> charities = charityRepository.findAll();
        if (charities.size() == 0) {
            throw new Exception("No Charities listed");
        }
        return charities;
    }

    @Override
    public Charity getCharity(UUID uuid) throws Exception {
        Charity charity = charityRepository.findById(uuid)
                .orElseThrow(() ->  new Exception("Charity not Found"));
        return charity;
    }

    @Override
    public void updateCharity(UUID uuid, CharityDTO charityDTO) throws Exception {
        Charity charityToUpdate = charityRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Charity not Found"));
        if (!charityDTO.getName().isEmpty() && !charityDTO.getName().equals(charityToUpdate.getName()))
            charityToUpdate.setName(charityDTO.getName());
        if (!charityDTO.getDescription().isEmpty() && !charityDTO.getDescription().equals(charityToUpdate.getDescription()))
            charityToUpdate.setDescription(charityDTO.getDescription());
        if (!charityDTO.getStatus().equals(charityToUpdate.getStatus()))
            charityToUpdate.setStatus(charityDTO.getStatus());
        if (!charityDTO.getCurrency().isEmpty() && !charityDTO.getCurrency().equals(charityToUpdate.getCurrency()))
            charityToUpdate.setCurrency(charityDTO.getCurrency());

        Address newAddress = new Address();
        newAddress.setStreet(charityDTO.getAddress().getStreet());
        newAddress.setCity(charityDTO.getAddress().getCity());
        newAddress.setState(charityDTO.getAddress().getState());
        newAddress.setCountry(charityDTO.getAddress().getCountry());
        newAddress.setZipCode(charityDTO.getAddress().getZipCode());

        if (!charityDTO.getAddress().equals(charityToUpdate.getAddress()))
            charityToUpdate.setAddress(newAddress);

        charityRepository.save(charityToUpdate);
    }

    @Override
    public Charity deleteCharity(UUID uuid) throws Exception {
        Charity charityToDelete = charityRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Charity not Found"));
        charityRepository.deleteById(uuid);
        return charityToDelete;
    }

    @Override
    public FundSummaryDTO getCharitableFunds(UUID uuid) throws Exception {
        Charity charity = charityRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Charity does not exist"));
        BigDecimal accumulatedFunds = donationRepository.sumDonatedAmount(uuid);
        if (accumulatedFunds == null) {
            accumulatedFunds = BigDecimal.valueOf(0);
        }

        FundSummaryDTO fundSummaryDTO = new FundSummaryDTO();
        fundSummaryDTO.setUuid(charity.getUuid());
        fundSummaryDTO.setEntityName(charity.getName());
        fundSummaryDTO.setFundAmount(accumulatedFunds);
        fundSummaryDTO.setDateRetrieve(LocalDateTime.now());

        return fundSummaryDTO;
    }

}
