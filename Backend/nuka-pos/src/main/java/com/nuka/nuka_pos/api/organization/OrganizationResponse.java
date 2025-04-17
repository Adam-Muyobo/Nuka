package com.nuka.nuka_pos.api.organization;

import lombok.*;

/**
 * DTO for transferring Organization data between service and controller.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrganizationResponse {

    private Long organizationId;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String country;
    private String businessType;
    private Boolean smartInvoicingEnabled;
    private Boolean barcodeEnabled;
    private String receiptMode;
    private String taxNumber;
    private String logoPath;
    private Boolean isVerified;
    private String verificationCode;
    private String createdAt;
}
