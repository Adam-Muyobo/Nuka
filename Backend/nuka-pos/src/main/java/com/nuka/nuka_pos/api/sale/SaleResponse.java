package com.nuka.nuka_pos.api.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponse {
    private Long id;
    private Long organizationId;
    private Long branchId;
    private Long customerId;
    private Long userId;
    private LocalDateTime saleDate;
    private Boolean isPaid;
    private String status; // Using String representation of the enum (COMPLETED, PENDING, CANCELLED)
}
