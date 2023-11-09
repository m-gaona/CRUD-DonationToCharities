package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.*;
import dev.marlon.CRUDDonationToCharities.model.Address;
import dev.marlon.CRUDDonationToCharities.model.Charity;
import dev.marlon.CRUDDonationToCharities.model.Donation;
import dev.marlon.CRUDDonationToCharities.model.Donor;
import dev.marlon.CRUDDonationToCharities.repository.CharityRepository;
import dev.marlon.CRUDDonationToCharities.repository.DonationRepository;
import dev.marlon.CRUDDonationToCharities.repository.DonorRepository;
import dev.marlon.CRUDDonationToCharities.util.CharityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DonationServiceImpl implements DonationService{

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final CharityRepository charityRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository, DonorRepository donorRepository, CharityRepository charityRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.charityRepository = charityRepository;
    }

    @Override
    public List<DonationResponse> getAllDonations() throws Exception {
        List<Donation> donations = donationRepository.findAll();
        if (donations.size() == 0) {
            throw new Exception("No Donations listed");
        }

        List<DonationResponse> donationResponses = new ArrayList<>();

        for (Donation donation : donations) {
            Donor donor = donation.getDonor();
            Charity charity = donation.getCharity();
            Address address = charity.getAddress();

            DonorDTO donorDTO = new DonorDTO();
            donorDTO.setFirstName(donor.getFirstName());
            donorDTO.setLastName(donor.getLastName());
            donorDTO.setEmail(donor.getEmail());
            donorDTO.setDateOfBirth(donor.getDateOfBirth());
            donorDTO.setBalance(donor.getBalance());

            CharityDTO charityDTO = new CharityDTO();
            charityDTO.setName(charity.getName());
            charityDTO.setDescription(charity.getDescription());
            charityDTO.setCurrency(charity.getCurrency());
            charityDTO.setStatus(charity.getStatus());

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(address.getStreet());
            addressDTO.setCity(address.getCity());
            addressDTO.setState(address.getState());
            addressDTO.setCountry(address.getCountry());
            addressDTO.setZipCode(address.getZipCode());

            charityDTO.setAddress(addressDTO);

            DonationResponse donationResponse = new DonationResponse();
            donationResponse.setUuid(donation.getUuid());
            donationResponse.setDonorDTO(donorDTO);
            donationResponse.setCharityDTO(charityDTO);
            donationResponse.setAmount(donation.getAmount());
            donationResponse.setTimestamp(donation.getTimestamp());
            donationResponse.setReferenceNumber(donation.getReferenceNumber());

            donationResponses.add(donationResponse);
        }

        return donationResponses;
    }

    @Override
    @Transactional
    public DonationResponse viewDonation(String referenceNumber) throws Exception {
        Donation donation = donationRepository.findByReferenceNumber(referenceNumber);
        if (donation == null) {
            throw new Exception("Donation not found for reference number: " + referenceNumber);
        }

        DonorDTO donorDTO = new DonorDTO();
        donorDTO.setFirstName(donation.getDonor().getFirstName());
        donorDTO.setLastName(donation.getDonor().getLastName());
        donorDTO.setEmail(donation.getDonor().getEmail());
        donorDTO.setDateOfBirth(donation.getDonor().getDateOfBirth());
        donorDTO.setBalance(donation.getDonor().getBalance());

        CharityDTO charityDTO = new CharityDTO();
        charityDTO.setName(donation.getCharity().getName());
        charityDTO.setDescription(donation.getCharity().getDescription());
        charityDTO.setCurrency(donation.getCharity().getCurrency());
        charityDTO.setStatus(donation.getCharity().getStatus());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(donation.getCharity().getAddress().getStreet());
        addressDTO.setCity(donation.getCharity().getAddress().getCity());
        addressDTO.setState(donation.getCharity().getAddress().getState());
        addressDTO.setCountry(donation.getCharity().getAddress().getCountry());
        addressDTO.setZipCode(donation.getCharity().getAddress().getZipCode());
        charityDTO.setAddress(addressDTO);


        DonationResponse donationResponse = new DonationResponse();
        donationResponse.setUuid(donation.getUuid());
        donationResponse.setDonorDTO(donorDTO);
        donationResponse.setCharityDTO(charityDTO);
        donationResponse.setAmount(donation.getAmount());
        donationResponse.setTimestamp(donation.getTimestamp());
        donationResponse.setReferenceNumber(donation.getReferenceNumber());

        return donationResponse;
    }

    @Override
    public DonationResponse sendDonation(DonationRequest request) throws Exception {
        UUID donorId = request.getDonor().getUuid();
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new Exception("Donor does not exist"));

        UUID charityId = request.getCharity().getUuid();
        Charity charity = charityRepository.findById(charityId)
                .orElseThrow(() -> new Exception("Charity does not exist"));

        if (charity.getStatus() != CharityStatus.ACTIVE) {
            throw new Exception("Charity is not active; donations are not allowed.");
        }

        BigDecimal donationAmount = request.getAmount();
        BigDecimal balance = donor.getBalance();
        if (donationAmount == null || donationAmount.compareTo(BigDecimal.ZERO) <= 0 || donationAmount.compareTo(balance) > 0) {
            throw new Exception("Invalid donation amount");
        }

        UUID generateReferenceNumber = UUID.randomUUID();
        String referenceNumber = generateReferenceNumber.toString().replace("-", "").substring(0, 8);

        donor.setBalance(balance.subtract(donationAmount));

        Donation newDonation = new Donation();
        newDonation.setAmount(donationAmount);
        newDonation.setDonor(donor);
        newDonation.setCharity(charity);
        newDonation.setTimestamp(LocalDateTime.now());
        newDonation.setReferenceNumber(referenceNumber);
        Donation savedDonation = donationRepository.save(newDonation);

        DonorDTO donorDTO = new DonorDTO();
        donorDTO.setFirstName(savedDonation.getDonor().getFirstName());
        donorDTO.setLastName(savedDonation.getDonor().getLastName());
        donorDTO.setEmail(savedDonation.getDonor().getEmail());
        donorDTO.setDateOfBirth(savedDonation.getDonor().getDateOfBirth());
        donorDTO.setBalance(savedDonation.getDonor().getBalance());

        CharityDTO charityDTO = new CharityDTO();
        charityDTO.setName(savedDonation.getCharity().getName());
        charityDTO.setDescription(savedDonation.getCharity().getDescription());
        charityDTO.setCurrency(savedDonation.getCharity().getCurrency());
        charityDTO.setStatus(savedDonation.getCharity().getStatus());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(savedDonation.getCharity().getAddress().getStreet());
        addressDTO.setCity(savedDonation.getCharity().getAddress().getCity());
        addressDTO.setState(savedDonation.getCharity().getAddress().getState());
        addressDTO.setCountry(savedDonation.getCharity().getAddress().getCountry());
        addressDTO.setZipCode(savedDonation.getCharity().getAddress().getZipCode());
        charityDTO.setAddress(addressDTO);

        DonationResponse donationResponse = new DonationResponse();
        donationResponse.setUuid(savedDonation.getUuid());
        donationResponse.setDonorDTO(donorDTO);
        donationResponse.setCharityDTO(charityDTO);
        donationResponse.setAmount(savedDonation.getAmount());
        donationResponse.setTimestamp(savedDonation.getTimestamp());
        donationResponse.setReferenceNumber(savedDonation.getReferenceNumber());

        return donationResponse;
    }


    @Override
    @Transactional
    public void updateDonation(DonationRequest request, String referenceNumber) throws Exception {
        Donation donationToUpdate = donationRepository.findByReferenceNumber(referenceNumber);
        if (donationToUpdate == null) {
            throw new Exception("Donation does not exist");
        }

        BigDecimal updatedDonationAmount = request.getAmount();
        BigDecimal originalDonationAmount = donationToUpdate.getAmount();
        BigDecimal balance = donationToUpdate.getDonor().getBalance();
        if (updatedDonationAmount == null || updatedDonationAmount.compareTo(BigDecimal.ZERO) <= 0 || updatedDonationAmount.compareTo(balance) > 0) {
            throw new Exception("Invalid donation amount");
        }

        BigDecimal donationDifference = updatedDonationAmount.subtract(originalDonationAmount);
        BigDecimal updatedBalance = balance.subtract(donationDifference);

        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Insufficient balance");
        }

        donationToUpdate.getDonor().setBalance(updatedBalance);
        donationToUpdate.setAmount(updatedDonationAmount);

        Charity charityToUpdate = charityRepository.findById(donationToUpdate.getCharity().getUuid())
                .orElseThrow(() -> new Exception("Charity does not exist"));

        Charity newCharity = charityRepository.findById(request.getCharity().getUuid())
                .orElseThrow(() -> new Exception("Charity does not exist"));

        if(!donationToUpdate.getCharity().equals(newCharity) && charityToUpdate != null)
            donationToUpdate.setCharity(newCharity);

        donationToUpdate.setTimestamp(LocalDateTime.now());

        donationRepository.save(donationToUpdate);
    }

    @Override
    @Transactional
    public DonationResponse cancelDonation(String referenceNumber) throws Exception {
        Donation donationToCancel = donationRepository.findByReferenceNumber(referenceNumber);
        if (donationToCancel == null) {
            throw new Exception("Donation does not exist");
        }

        BigDecimal cancelledAmount = donationToCancel.getAmount();
        BigDecimal balance = donationToCancel.getDonor().getBalance();

        BigDecimal updatedBalance = balance.add(cancelledAmount);
        Donor donor = donationToCancel.getDonor();

        donor.setBalance(updatedBalance);
        donorRepository.save(donor);

        donationRepository.delete(donationToCancel);

        DonorDTO donorDTO = new DonorDTO();
        donorDTO.setFirstName(donationToCancel.getDonor().getFirstName());
        donorDTO.setLastName(donationToCancel.getDonor().getLastName());
        donorDTO.setEmail(donationToCancel.getDonor().getEmail());
        donorDTO.setDateOfBirth(donationToCancel.getDonor().getDateOfBirth());
        donorDTO.setBalance(donationToCancel.getDonor().getBalance());

        CharityDTO charityDTO = new CharityDTO();
        charityDTO.setName(donationToCancel.getCharity().getName());
        charityDTO.setDescription(donationToCancel.getCharity().getDescription());
        charityDTO.setCurrency(donationToCancel.getCharity().getCurrency());
        charityDTO.setStatus(donationToCancel.getCharity().getStatus());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(donationToCancel.getCharity().getAddress().getStreet());
        addressDTO.setCity(donationToCancel.getCharity().getAddress().getCity());
        addressDTO.setState(donationToCancel.getCharity().getAddress().getState());
        addressDTO.setCountry(donationToCancel.getCharity().getAddress().getCountry());
        addressDTO.setZipCode(donationToCancel.getCharity().getAddress().getZipCode());
        charityDTO.setAddress(addressDTO);

        DonationResponse donationResponse = new DonationResponse();
        donationResponse.setUuid(donationToCancel.getUuid());
        donationResponse.setDonorDTO(donorDTO);
        donationResponse.setCharityDTO(charityDTO);
        donationResponse.setAmount(donationToCancel.getAmount());
        donationResponse.setTimestamp(donationToCancel.getTimestamp());
        donationResponse.setReferenceNumber(donationToCancel.getReferenceNumber());

        return donationResponse;
    }
}
