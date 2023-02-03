package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.UUID;

/**
 * RFID tags that uses during the charge session of a vehicle
 */
@Getter
@Setter
@Entity
@Table(name = "rfid_tags")
public class RfIdTag {

    /**
     * RFID tag ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * RFID tag name
     */
    @Length(max = 255)
    @Column(name = "tag_name", nullable = false)
    private String tagName;

    /**
     * RFID tag global unique number
     */
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "tag_number", columnDefinition = "CHAR(36)", nullable = false)
    private UUID tagNumber;

    /**
     * Customer that owned RFID tag
     */
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
}
