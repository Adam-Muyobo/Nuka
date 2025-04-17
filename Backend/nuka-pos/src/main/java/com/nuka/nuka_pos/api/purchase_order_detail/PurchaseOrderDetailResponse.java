package com.nuka.nuka_pos.api.purchase_order_detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailResponse {

    private Long id;
    private Long purchaseOrderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
