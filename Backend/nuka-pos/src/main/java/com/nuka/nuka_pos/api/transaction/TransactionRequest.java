package com.nuka.nuka_pos.api.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionRequest {

    // Getters and Setters
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;

}
