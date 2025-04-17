package com.nuka.nuka_pos.api.sale_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemResponse {
    private Long id;
    private Long saleId;
    private Long productId;  // Nullable
    private Long serviceId;  // Nullable
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice; // This will be calculated: quantity * unitPrice
}
