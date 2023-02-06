package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Sessions of charging vehicles
 */
@Getter
@Setter
@Entity
@Table(name = "charging_sessions")
@EntityListeners(AuditingEntityListener.class)
public class ChargingSession {

    /**
     * Session ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Date of create session
     */
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * Date of modified session
     */
    @Column(name = "last_modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

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
