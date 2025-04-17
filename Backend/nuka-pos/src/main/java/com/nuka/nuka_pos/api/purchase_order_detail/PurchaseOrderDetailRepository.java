package com.nuka.nuka_pos.api.purchase_order_detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Long> {

    List<PurchaseOrderDetail> findByPurchaseOrderId(Long purchaseOrderId);
}
