package com.nuka.nuka_pos.api.purchase_order_detail;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-details")
public class PurchaseOrderDetailController {

    private final PurchaseOrderDetailService detailService;

    public PurchaseOrderDetailController(PurchaseOrderDetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping
    public List<PurchaseOrderDetailResponse> getAllDetails() {
        return detailService.getAllDetails();
    }

    @GetMapping("/{id}")
    public PurchaseOrderDetailResponse getDetailById(@PathVariable Long id) {
        return detailService.getDetailById(id);
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public List<PurchaseOrderDetailResponse> getDetailsByPurchaseOrder(@PathVariable Long purchaseOrderId) {
        return detailService.getDetailsByPurchaseOrder(purchaseOrderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDetail(@RequestBody PurchaseOrderDetail detail) {
        detailService.createDetail(detail);
    }

    @PutMapping("/{id}")
    public void updateDetail(@PathVariable Long id, @RequestBody PurchaseOrderDetail updatedData) {
        detailService.updateDetail(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDetail(@PathVariable Long id) {
        detailService.deleteDetail(id);
    }
}
