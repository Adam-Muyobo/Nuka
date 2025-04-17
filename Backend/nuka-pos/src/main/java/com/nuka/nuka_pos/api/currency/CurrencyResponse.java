package com.nuka.nuka_pos.api.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CurrencyResponse is a Data Transfer Object (DTO) used for returning currency details
 * between the controller and the service layer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    private Long id;
    private String name;
    private String symbol;
}
