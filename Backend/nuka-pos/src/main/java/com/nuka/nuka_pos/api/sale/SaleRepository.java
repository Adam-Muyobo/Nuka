package com.nuka.nuka_pos.api.sale;

import com.nuka.nuka_pos.api.sale.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Find sales by status
    List<Sale> findByStatus(SaleStatus status);

    // Find sales by branch
    List<Sale> findByBranchId(Long branchId);

    // Find sales by customer
    List<Sale> findByCustomerId(Long customerId);
}
