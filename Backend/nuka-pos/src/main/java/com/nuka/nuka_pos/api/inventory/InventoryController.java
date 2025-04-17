package com.nuka.nuka_pos.api.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryResponse getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/organization/{organizationId}")
    public List<InventoryResponse> getInventoryByOrganization(@PathVariable Long organizationId) {
        return inventoryService.getInventoryByOrganization(organizationId);
    }

    @GetMapping("/branch/{branchId}")
    public List<InventoryResponse> getInventoryByBranch(@PathVariable Long branchId) {
        return inventoryService.getInventoryByBranch(branchId);
    }

    @GetMapping("/product/{productId}")
    public List<InventoryResponse> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @GetMapping("/status/{status}")
    public List<InventoryResponse> getInventoryByStatus(@PathVariable String status) {
        return inventoryService.getInventoryByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody Inventory inventory) {
        inventoryService.createInventory(inventory);
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable Long id, @RequestBody Inventory updatedData) {
        inventoryService.updateInventory(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}
