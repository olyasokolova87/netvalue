package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * User roles
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
