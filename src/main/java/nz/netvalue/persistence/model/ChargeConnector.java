package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * Connectors in charge point
 */
@Entity
@Table(name = "charge_connectors")
public class ChargeConnector {

    /**
     * Charge connector ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Charge connector number in point
     */
    @Column(name = "connector_number", nullable = false)
    private Long connectorNumber;

    /**
     * The meter value of charges Wh of energy
     */
    @Column(name = "meter_value", nullable = false)
    private Integer meterValue;

    /**
     * Charge point that owns the connector
     */
    @ManyToOne
    @JoinColumn(name = "charge_point_id")
    private ChargePoint chargePoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConnectorNumber() {
        return connectorNumber;
    }

    public void setConnectorNumber(Long connectorNumber) {
        this.connectorNumber = connectorNumber;
    }

    public Integer getMeterValue() {
        return meterValue;
    }

    public void setMeterValue(Integer meterValue) {
        this.meterValue = meterValue;
    }

    public ChargePoint getChargePoint() {
        return chargePoint;
    }

    public void setChargePoint(ChargePoint chargePoint) {
        this.chargePoint = chargePoint;
    }
}
