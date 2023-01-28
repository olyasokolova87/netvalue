package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * Point where vehicle is charging
 */
@Entity
@Table(name = "charge_point")
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
    @Column(name = "serialNumber", nullable = false)
    private String serialNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}