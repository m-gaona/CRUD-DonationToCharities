package dev.marlon.CRUDDonationToCharities.dto;

import dev.marlon.CRUDDonationToCharities.util.CharityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharityDTO {
    private String name;
    private String description;
    private String currency;
    private CharityStatus status;
    private AddressDTO address;
}
