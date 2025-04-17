package com.nuka.nuka_pos.api.taxtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaxTypeResponse is used to send a TaxType to the client in response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxTypeResponse {

    private Long id;
    private String name;  // e.g., "VAT", "NONE"
}
