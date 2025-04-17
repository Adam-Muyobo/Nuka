package com.nuka.nuka_pos.api.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for returning customer data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String taxNumber;
    private String customerType;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long organizationId;
    private Long branchId;
}
