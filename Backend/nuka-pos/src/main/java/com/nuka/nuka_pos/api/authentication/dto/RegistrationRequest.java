package com.nuka.nuka_pos.api.authentication.dto;

import com.nuka.nuka_pos.api.organization.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for organization and root admin registration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    // Organization info
    private String organizationName;
    private String organizationCode;
    private String organizationEmail;
    private String organizationPhone;
    private String country;
    private Organization.BusinessType businessType;
    private Boolean smartInvoicingEnabled;
    private Boolean barcodeEnabled;
    private Organization.ReceiptMode receiptMode;
    private String taxNumber;

    // Root admin user info
    private String adminUsername;
    private String adminPassword;
    private String adminForenames;
    private String adminSurname;
    private String adminEmail;
    private String adminPhone;
}
