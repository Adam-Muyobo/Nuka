package com.nuka.nuka_pos.api.inventory;

import com.nuka.nuka_pos.api.inventory.enums.InventoryStatus;
import com.nuka.nuka_pos.api.inventory.exceptions.InventoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InventoryResponse getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + id));
        return mapToResponse(inventory);
    }

    public List<InventoryResponse> getInventoryByOrganization(Long organizationId) {
        return inventoryRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getInventoryByBranch(Long branchId) {
        return inventoryRepository.findByBranchId(branchId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getInventoryByProduct(Long productId) {
        return inventoryRepository.findByProductId(productId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getInventoryByStatus(String status) {
        InventoryStatus inventoryStatus = InventoryStatus.valueOf(status.toUpperCase());
        return inventoryRepository.findByInventoryStatus(inventoryStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public void updateInventory(Long id, Inventory updatedData) {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + id));

        if (updatedData.getQuantityAvailable() != 0) existing.setQuantityAvailable(updatedData.getQuantityAvailable());
        if (updatedData.getQuantityReserved() != 0) existing.setQuantityReserved(updatedData.getQuantityReserved());
        if (updatedData.getQuantitySold() != 0) existing.setQuantitySold(updatedData.getQuantitySold());
        if (updatedData.getCost() != null) existing.setCost(updatedData.getCost());
        if (updatedData.getInventoryStatus() != null) existing.setInventoryStatus(updatedData.getInventoryStatus());
        if (updatedData.getProduct() != null) existing.setProduct(updatedData.getProduct());
        if (updatedData.getOrganization() != null) existing.setOrganization(updatedData.getOrganization());
        if (updatedData.getBranch() != null) existing.setBranch(updatedData.getBranch());

        inventoryRepository.save(existing);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getOrganization().getId(),
                inventory.getProduct().getId(),
                inventory.getBranch().getId(),
                inventory.getQuantityAvailable(),
                inventory.getQuantityReserved(),
                inventory.getQuantitySold(),
                inventory.getCost(),
                inventory.getInventoryStatus().name(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }
}
