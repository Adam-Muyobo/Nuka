package com.nuka.nuka_pos.api.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {

    private Long id;
    private Long saleId;
    private String type; // From enum, serialized as string
    private LocalDateTime receiptDate;
    private String receiptNumber;
    private String recipientEmail;
    private Boolean delivered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
