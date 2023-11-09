package dev.marlon.CRUDDonationToCharities.repository;

import dev.marlon.CRUDDonationToCharities.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
