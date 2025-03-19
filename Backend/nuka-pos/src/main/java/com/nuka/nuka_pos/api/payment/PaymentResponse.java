package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Long transactionId;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
}
