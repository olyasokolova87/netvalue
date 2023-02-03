package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 15)
    private String roleName;
}
