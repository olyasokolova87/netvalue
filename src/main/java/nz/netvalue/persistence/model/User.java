package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * Users
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
