package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * Vehicles that need to be charged
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }
}
