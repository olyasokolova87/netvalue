package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Sessions of charging vehicles
 */
@Getter
@Setter
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
    @Length(max = 500)
    @Column(name = "error_message")
    private String errorMessage;

    /**
     * Linked vehicle to RFID tag
     */
    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}
