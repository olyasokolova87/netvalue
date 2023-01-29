package nz.netvalue.persistence.model;

import javax.persistence.*;

/**
 * Customer that use charge point
 */
@Entity
@Table(name = "customer")
public class Customer {

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
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
