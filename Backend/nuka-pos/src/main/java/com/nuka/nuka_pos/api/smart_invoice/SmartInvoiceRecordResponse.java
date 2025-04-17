package com.nuka.nuka_pos.api.smart_invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartInvoiceRecordResponse {

    private Long id;
    private Long organizationId;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private Long customerId;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private String status;
    private String zraTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
