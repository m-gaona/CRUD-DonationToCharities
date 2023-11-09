package dev.marlon.CRUDDonationToCharities.repository;

import dev.marlon.CRUDDonationToCharities.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    Donation findByReferenceNumber(String referenceNumber);

    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.charity.id = :charityID")
    BigDecimal sumDonatedAmount(UUID charityID);
}
