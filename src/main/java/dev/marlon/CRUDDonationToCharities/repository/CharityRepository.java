package dev.marlon.CRUDDonationToCharities.repository;

import dev.marlon.CRUDDonationToCharities.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CharityRepository extends JpaRepository<Charity, UUID> {
    Charity findByName(String name);
}
