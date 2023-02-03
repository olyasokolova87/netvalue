package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Vehicles that need to be charged
 */
@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {

    /**
     * Vehicle ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Vehicles name
     */
    @Length(max = 255)
    @Column(name = "vehicle_name", nullable = false)
    private String vehicleName;

    /**
     * Vehicles registration plate
     */
    @Length(max = 15)
    @Column(name = "registration_plate", nullable = false)
    private String registrationPlate;
}
