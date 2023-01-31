package nz.netvalue.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Sessions of charging vehicles
 */
@Entity
@Table(name = "charging_sessions")
public class ChargingSession {

    /**
     * Session ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Date and time when session starts
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * Date and time when session ends
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * Connection that used during the session
     */
    @OneToOne
    @JoinColumn(name = "charge_connector_id", nullable = false)
    private ChargeConnector chargeConnector;

    /**
     * RFID tag that used during the session
     */
    @OneToOne
    @JoinColumn(name = "rfid_tag_id", nullable = false)
    private RfIdTag rfIdTag;

    /**
     * Error message when charge session can not complete
     */
    @Column(name = "error_message")
    private String errorMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ChargeConnector getChargeConnector() {
        return chargeConnector;
    }

    public void setChargeConnector(ChargeConnector chargeConnector) {
        this.chargeConnector = chargeConnector;
    }

    public RfIdTag getRfIdTag() {
        return rfIdTag;
    }

    public void setRfIdTag(RfIdTag rfIdTag) {
        this.rfIdTag = rfIdTag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
