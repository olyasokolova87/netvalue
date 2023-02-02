package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Points where vehicles is charging
 */
@Getter
@Setter
@Entity
@Table(name = "charge_points")
public class ChargePoint {

    /**
     * Charge point ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Charge point name
     */
    @Column(name = "point_name", nullable = false)
    private String pointName;

    /**
     * Charge point serial number
     */
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
}
