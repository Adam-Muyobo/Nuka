package com.nuka.nuka_pos.api.supplier;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SupplierResponse is used to send a Supplier to the client in response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String taxNumber;
    private Boolean isActive;
    private Long organizationId;

}
