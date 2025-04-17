package com.nuka.nuka_pos.api.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ServiceResponse is used to send a Service to the client in response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private Long id;
    private String serviceName;
    private String description;
    private BigDecimal price;
    private Boolean isActive;
    private Long organizationId;
    private Long categoryId;
}
