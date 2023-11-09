package dev.marlon.CRUDDonationToCharities.dto;

import dev.marlon.CRUDDonationToCharities.model.Donation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {

    private UUID uuid;
    private DonorDTO donorDTO;
    private CharityDTO charityDTO;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String referenceNumber;
}
