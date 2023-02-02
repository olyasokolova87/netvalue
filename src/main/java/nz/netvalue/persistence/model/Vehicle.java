package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "vehicle_name", nullable = false)
    private String vehicleName;

    /**
     * Vehicles registration plate
     */
    @Column(name = "registration_plate", nullable = false)
    private String registrationPlate;
}
