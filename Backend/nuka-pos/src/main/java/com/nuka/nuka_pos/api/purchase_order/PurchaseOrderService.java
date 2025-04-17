package com.nuka.nuka_pos.api.purchase_order;

import com.nuka.nuka_pos.api.purchase_order.enums.PurchaseOrderStatus;
import com.nuka.nuka_pos.api.purchase_order.exceptions.PurchaseOrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    // Get all purchase orders
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get purchase order by ID
    public PurchaseOrderResponse getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase order not found with id: " + id));
        return mapToResponse(purchaseOrder);
    }

    // Get purchase orders by status
    public List<PurchaseOrderResponse> getPurchaseOrdersByStatus(PurchaseOrderStatus status) {
        return purchaseOrderRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get purchase orders by organization ID
    public List<PurchaseOrderResponse> getPurchaseOrdersByOrganization(Long organizationId) {
        return purchaseOrderRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get purchase orders by supplier ID
    public List<PurchaseOrderResponse> getPurchaseOrdersBySupplier(Long supplierId) {
        return purchaseOrderRepository.findBySupplierId(supplierId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create new purchase order
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToResponse(savedOrder);
    }

    // Update existing purchase order
    @Transactional
    public PurchaseOrderResponse updatePurchaseOrder(Long id, PurchaseOrder updatedData) {
        PurchaseOrder existingOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase order not found with id: " + id));

        // Update fields with non-null data
        if (updatedData.getSupplier() != null) existingOrder.setSupplier(updatedData.getSupplier());
        if (updatedData.getOrganization() != null) existingOrder.setOrganization(updatedData.getOrganization());
        if (updatedData.getOrderDate() != null) existingOrder.setOrderDate(updatedData.getOrderDate());
        if (updatedData.getExpectedDeliveryDate() != null) existingOrder.setExpectedDeliveryDate(updatedData.getExpectedDeliveryDate());
        if (updatedData.getTotalAmount() != null) existingOrder.setTotalAmount(updatedData.getTotalAmount());
        if (updatedData.getStatus() != null) existingOrder.setStatus(updatedData.getStatus());

        purchaseOrderRepository.save(existingOrder);
        return mapToResponse(existingOrder);
    }

    // Helper method to map PurchaseOrder to PurchaseOrderResponse
    private PurchaseOrderResponse mapToResponse(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderResponse(
                purchaseOrder.getId(),
                purchaseOrder.getSupplier().getId(),
                purchaseOrder.getOrganization().getId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getExpectedDeliveryDate(),
                purchaseOrder.getTotalAmount(),
                purchaseOrder.getStatus().toString(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getUpdatedAt()
        );
    }
}
