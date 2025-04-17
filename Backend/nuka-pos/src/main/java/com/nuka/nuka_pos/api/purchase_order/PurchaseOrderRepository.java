package com.nuka.nuka_pos.api.purchase_order;

import com.nuka.nuka_pos.api.purchase_order.enums.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    // Find purchase orders by status
    List<PurchaseOrder> findByStatus(PurchaseOrderStatus status);

    // Find purchase orders by organization ID
    List<PurchaseOrder> findByOrganizationId(Long organizationId);

    // Find purchase orders by supplier ID
    List<PurchaseOrder> findBySupplierId(Long supplierId);
}
