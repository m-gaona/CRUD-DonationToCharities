package dev.marlon.CRUDDonationToCharities.dto;

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
public class FundSummaryDTO {
    private UUID uuid;
    private String entityName;
    private BigDecimal fundAmount;
    private LocalDateTime dateRetrieve;
}
