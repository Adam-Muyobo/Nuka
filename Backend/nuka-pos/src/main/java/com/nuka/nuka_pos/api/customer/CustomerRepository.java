package com.nuka.nuka_pos.api.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByOrganizationId(Long organizationId);
    List<Customer> findByBranchId(Long branchId);
}