package com.nuka.nuka_pos.api.purchase_order;

import com.nuka.nuka_pos.api.purchase_order.enums.PurchaseOrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/{id}")
    public PurchaseOrderResponse getPurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    @GetMapping("/status/{status}")
    public List<PurchaseOrderResponse> getPurchaseOrdersByStatus(@PathVariable String status) {
        PurchaseOrderStatus statusEnum = PurchaseOrderStatus.valueOf(status.toUpperCase());
        return purchaseOrderService.getPurchaseOrdersByStatus(statusEnum);
    }

    @GetMapping("/organization/{organizationId}")
    public List<PurchaseOrderResponse> getPurchaseOrdersByOrganization(@PathVariable Long organizationId) {
        return purchaseOrderService.getPurchaseOrdersByOrganization(organizationId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderResponse> getPurchaseOrdersBySupplier(@PathVariable Long supplierId) {
        return purchaseOrderService.getPurchaseOrdersBySupplier(supplierId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderResponse createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.createPurchaseOrder(purchaseOrder);
    }

    @PutMapping("/{id}")
    public PurchaseOrderResponse updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder updatedData) {
        return purchaseOrderService.updatePurchaseOrder(id, updatedData);
    }
}
