package dev.marlon.CRUDDonationToCharities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID uuid;
    @Column(columnDefinition = "NUMERIC(20,2)")
    private BigDecimal amount;
    private LocalDateTime timestamp;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Donor donor;
    @ManyToOne()
    private Charity charity;
    private String referenceNumber;
}
