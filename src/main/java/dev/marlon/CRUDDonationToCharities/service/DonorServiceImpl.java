package dev.marlon.CRUDDonationToCharities.service;

import dev.marlon.CRUDDonationToCharities.dto.DonorDTO;
import dev.marlon.CRUDDonationToCharities.dto.FundSummaryDTO;
import dev.marlon.CRUDDonationToCharities.model.Donor;
import dev.marlon.CRUDDonationToCharities.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class DonorServiceImpl implements DonorService{
    private final DonorRepository donorRepository;
    @Autowired
    public DonorServiceImpl(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    public List<Donor> getAllDonors() throws Exception {
        List<Donor> donorList = donorRepository.findAll();
        if(donorList.size() == 0){
            throw new Exception("No Donors Listed");
        }
        return donorList;
    }

    @Override
    public Donor getDonor(UUID uuid) throws Exception {
        Donor donor = donorRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Donor not Found"));
        return donor;
    }

    @Override
    public Donor createDonor(DonorDTO donorDTO) throws Exception {
        Donor newDonor = new Donor();
        newDonor.setFirstName(donorDTO.getFirstName());
        newDonor.setLastName(donorDTO.getLastName());
        newDonor.setEmail(donorDTO.getEmail());
        newDonor.setDateOfBirth(donorDTO.getDateOfBirth());
        newDonor.setBalance(BigDecimal.valueOf(0));

        return donorRepository.save(newDonor);
    }

    @Override
    public void updateDonor(UUID uuid, DonorDTO donorDTO) throws Exception {
        Donor donorToUpdate = donorRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Donor does not exist"));
        if (!donorDTO.getFirstName().isEmpty() && !donorDTO.getFirstName().equals(donorToUpdate.getFirstName()))
            donorToUpdate.setFirstName(donorDTO.getFirstName());
        if(!donorDTO.getLastName().isEmpty() && !donorDTO.getLastName().equals(donorToUpdate.getLastName()))
            donorToUpdate.setLastName(donorDTO.getLastName());
        if(!donorDTO.getEmail().isEmpty() && !donorDTO.getEmail().equals(donorToUpdate.getEmail()))
            donorToUpdate.setEmail(donorDTO.getEmail());
        if(!donorDTO.getDateOfBirth().equals(donorToUpdate.getDateOfBirth()))
            donorToUpdate.setDateOfBirth(donorDTO.getDateOfBirth());
        donorRepository.save(donorToUpdate);
    }

    @Override
    public Donor fundBalance(UUID uuid, BigDecimal fund) throws Exception {
        Donor donorToUpdate = donorRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Donor does not exist"));
        BigDecimal newBalance = donorToUpdate.getBalance().add(fund);
        donorToUpdate.setBalance(newBalance);
        donorRepository.save(donorToUpdate);
        return donorToUpdate;
    }

    @Override
    public Donor deleteDonor(UUID uuid) throws Exception {
        Donor donorToDelete = donorRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Donor does not exist"));
        donorRepository.deleteById(uuid);
        return donorToDelete;
    }

    @Override
    public FundSummaryDTO checkBalance(UUID uuid) throws Exception {
        Donor donor = donorRepository.findById(uuid)
                .orElseThrow(() -> new Exception("Donor does not exist"));

        FundSummaryDTO fundSummaryDTO = new FundSummaryDTO();
        fundSummaryDTO.setUuid(donor.getUuid());
        fundSummaryDTO.setEntityName(donor.getFirstName() +" "+donor.getLastName());
        fundSummaryDTO.setFundAmount(donor.getBalance());
        fundSummaryDTO.setDateRetrieve(LocalDateTime.now());

        return fundSummaryDTO;
    }
}
