package nz.netvalue.persistence.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * RFID tag that uses during the charge session of a vehicle
 */
@Entity
@Table(name = "rfid_tag")
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
    private Customer customer;

    /**
     * Linked vehicle to RFID tag
     */
    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public UUID getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(UUID tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
