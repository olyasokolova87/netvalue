package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Users
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * User name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * User login
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * User password
     */
    @Column(name = "user_password", nullable = false)
    private String password;

    /**
     * User role
     */
    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
