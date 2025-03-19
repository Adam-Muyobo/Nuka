package com.nuka.nuka_pos.api.transaction_item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItemRequest {
    private Long transactionId;
    private Long productId;
    private Integer quantity;
}
