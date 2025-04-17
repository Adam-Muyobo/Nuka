package com.nuka.nuka_pos.api.sale;

import com.nuka.nuka_pos.api.customer.Customer;
import com.nuka.nuka_pos.api.branch.Branch;
import com.nuka.nuka_pos.api.organization.Organization;
import com.nuka.nuka_pos.api.sale.enums.SaleStatus;
import com.nuka.nuka_pos.api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime saleDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean isPaid = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;  // Enum for sale status (COMPLETED, PENDING, CANCELLED)

}
