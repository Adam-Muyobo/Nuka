package com.nuka.nuka_pos.api.sale_item;

import com.nuka.nuka_pos.api.sale_item.exceptions.SaleItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleItemService {

    private final SaleItemRepository saleItemRepository;

    public SaleItemService(SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

    public List<SaleItemResponse> getAllSaleItems() {
        return saleItemRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SaleItemResponse getSaleItemById(Long id) {
        SaleItem item = saleItemRepository.findById(id)
                .orElseThrow(() -> new SaleItemNotFoundException("Sale item not found with id: " + id));
        return mapToResponse(item);
    }

    public List<SaleItemResponse> getSaleItemsBySaleId(Long saleId) {
        return saleItemRepository.findBySaleId(saleId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createSaleItem(SaleItem saleItem) {
        // Enforce mutual exclusivity between product and service
        boolean hasProduct = saleItem.getProduct() != null;
        boolean hasService = saleItem.getService() != null;

        if (hasProduct == hasService) { // either both null or both not null
            throw new IllegalArgumentException("SaleItem must have either a product or a service, but not both.");
        }

        saleItemRepository.save(saleItem);
    }

    public void updateSaleItem(Long id, SaleItem updatedData) {
        SaleItem existingItem = saleItemRepository.findById(id)
                .orElseThrow(() -> new SaleItemNotFoundException("Sale item not found with id: " + id));

        if (updatedData.getSale() != null) existingItem.setSale(updatedData.getSale());
        if (updatedData.getProduct() != null && updatedData.getService() == null) {
            existingItem.setProduct(updatedData.getProduct());
            existingItem.setService(null);
        } else if (updatedData.getService() != null && updatedData.getProduct() == null) {
            existingItem.setService(updatedData.getService());
            existingItem.setProduct(null);
        } else {
            throw new IllegalArgumentException("SaleItem can only be linked to either a product or a service, not both.");
        }

        if (updatedData.getQuantity() != null) existingItem.setQuantity(updatedData.getQuantity());
        if (updatedData.getUnitPrice() != null) existingItem.setUnitPrice(updatedData.getUnitPrice());

        saleItemRepository.save(existingItem);
    }

    public void deleteSaleItem(Long id) {
        if (!saleItemRepository.existsById(id)) {
            throw new SaleItemNotFoundException("Sale item not found with id: " + id);
        }
        saleItemRepository.deleteById(id);
    }

    private SaleItemResponse mapToResponse(SaleItem item) {
        return new SaleItemResponse(
                item.getId(),
                item.getSale().getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getService() != null ? item.getService().getId() : null,
                item.getQuantity(),
                item.getUnitPrice(),
                item.getQuantity() * item.getUnitPrice()
        );
    }
}
