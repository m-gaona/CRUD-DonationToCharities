package dev.marlon.CRUDDonationToCharities.model;

import dev.marlon.CRUDDonationToCharities.util.CharityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "charities")
public class Charity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharityStatus status;
    private LocalDateTime dateCreated;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charity_address_uuid", referencedColumnName = "uuid")
    private Address address;
    @OneToOne()
    @JoinColumn(name = "image_uuid", referencedColumnName = "uuid")
    private Image image;
}
