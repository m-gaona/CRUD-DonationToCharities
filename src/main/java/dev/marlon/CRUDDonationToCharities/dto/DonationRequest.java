package dev.marlon.CRUDDonationToCharities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {
    private BigDecimal amount;
    private DonationDonorDTO donor;
    private DonationCharityDTO charity;
    private LocalDateTime timestamps;
}
