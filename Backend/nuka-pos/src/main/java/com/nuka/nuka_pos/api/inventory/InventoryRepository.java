package com.nuka.nuka_pos.api.inventory;

import com.nuka.nuka_pos.api.inventory.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByOrganizationId(Long organizationId);

    List<Inventory> findByBranchId(Long branchId);

    List<Inventory> findByProductId(Long productId);

    List<Inventory> findByInventoryStatus(InventoryStatus inventoryStatus);
}
