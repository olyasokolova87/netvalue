package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * User roles
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Customer ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Customer name
     */
    @Column(name = "role_name", nullable = false)
    private String roleName;
}
