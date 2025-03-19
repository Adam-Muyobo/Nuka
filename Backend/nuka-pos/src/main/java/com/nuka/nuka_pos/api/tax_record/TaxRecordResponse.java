package com.nuka.nuka_pos.api.tax_record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxRecordResponse {

    private Long id;
    private Long transactionId;
    private BigDecimal vatAmount;
    private Boolean submittedToZRA;
}

