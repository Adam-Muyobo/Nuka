package com.nuka.nuka_pos.api.purchase_order_detail;

import com.nuka.nuka_pos.api.purchase_order_detail.exceptions.PurchaseOrderDetailNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderDetailService {

    private final PurchaseOrderDetailRepository detailRepository;

    public PurchaseOrderDetailService(PurchaseOrderDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public List<PurchaseOrderDetailResponse> getAllDetails() {
        return detailRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PurchaseOrderDetailResponse getDetailById(Long id) {
        PurchaseOrderDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderDetailNotFoundException("Detail not found with id: " + id));
        return mapToResponse(detail);
    }

    public List<PurchaseOrderDetailResponse> getDetailsByPurchaseOrder(Long purchaseOrderId) {
        return detailRepository.findByPurchaseOrderId(purchaseOrderId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createDetail(PurchaseOrderDetail detail) {
        detailRepository.save(detail);
    }

    public void updateDetail(Long id, PurchaseOrderDetail updatedData) {
        PurchaseOrderDetail existing = detailRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderDetailNotFoundException("Detail not found with id: " + id));

        if (updatedData.getQuantity() != null) existing.setQuantity(updatedData.getQuantity());
        if (updatedData.getUnitPrice() != null) existing.setUnitPrice(updatedData.getUnitPrice());
        if (updatedData.getProduct() != null) existing.setProduct(updatedData.getProduct());
        if (updatedData.getPurchaseOrder() != null) existing.setPurchaseOrder(updatedData.getPurchaseOrder());

        detailRepository.save(existing);
    }

    public void deleteDetail(Long id) {
        if (!detailRepository.existsById(id)) {
            throw new PurchaseOrderDetailNotFoundException("Detail not found with id: " + id);
        }
        detailRepository.deleteById(id);
    }

    private PurchaseOrderDetailResponse mapToResponse(PurchaseOrderDetail detail) {
        return new PurchaseOrderDetailResponse(
                detail.getId(),
                detail.getPurchaseOrder().getId(),
                detail.getProduct().getId(),
                detail.getQuantity(),
                detail.getUnitPrice()
        );
    }
}
