package com.nuka.nuka_pos.api.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;
    private Long organizationId;
    private Long productId;
    private Long branchId;
    private Integer quantityAvailable;
    private Integer quantityReserved;
    private Integer quantitySold;
    private BigDecimal cost;
    private String inventoryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
