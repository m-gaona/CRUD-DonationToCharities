package dev.marlon.CRUDDonationToCharities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID uuid;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
    private String fileName;
    private String contentType;
}
