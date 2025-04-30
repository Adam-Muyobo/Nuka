package com.nuka.nuka_pos.api.organization;

import com.nuka.nuka_pos.api.organization.exceptions.OrganizationNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing organizations.
 */
@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrganizationResponse getOrganizationById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + id));
        return mapToResponse(organization);
    }

    public void createOrganization(Organization organization) {
        organization.setCreatedAt(LocalDateTime.now());
        organizationRepository.save(organization);
    }

    public void updateOrganization(Long id, Organization updatedData) {
        Organization existing = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + id));

        if (updatedData.getName() != null) existing.setName(updatedData.getName());
        if (updatedData.getCode() != null) existing.setCode(updatedData.getCode());
        if (updatedData.getEmail() != null) existing.setEmail(updatedData.getEmail());
        if (updatedData.getPhone() != null) existing.setPhone(updatedData.getPhone());
        if (updatedData.getCountry() != null) existing.setCountry(updatedData.getCountry());
        if (updatedData.getBusinessType() != null) existing.setBusinessType(updatedData.getBusinessType());
        if (updatedData.getSmartInvoicingEnabled() != null) existing.setSmartInvoicingEnabled(updatedData.getSmartInvoicingEnabled());
        if (updatedData.getBarcodeEnabled() != null) existing.setBarcodeEnabled(updatedData.getBarcodeEnabled());
        if (updatedData.getReceiptMode() != null) existing.setReceiptMode(updatedData.getReceiptMode());
        if (updatedData.getTaxNumber() != null) existing.setTaxNumber(updatedData.getTaxNumber());
        if (updatedData.getLogoPath() != null) existing.setLogoPath(updatedData.getLogoPath());
        if (updatedData.getIsVerified() != null) existing.setIsVerified(updatedData.getIsVerified());
        if (updatedData.getVerificationCode() != null) existing.setVerificationCode(updatedData.getVerificationCode());

        organizationRepository.save(existing);
    }

    public void deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new OrganizationNotFoundException("Organization not found with id: " + id);
        }
        organizationRepository.deleteById(id);
    }

    public OrganizationResponse getOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByCode(code);
        if (organization == null) throw new OrganizationNotFoundException("Organization not found with code: " + code);
        return mapToResponse(organization);
    }


    private OrganizationResponse mapToResponse(Organization org) {
        return new OrganizationResponse(
                org.getId(),
                org.getName(),
                org.getCode(),
                org.getEmail(),
                org.getPhone(),
                org.getCountry(),
                org.getBusinessType().toString(),
                org.getSmartInvoicingEnabled(),
                org.getBarcodeEnabled(),
                org.getReceiptMode().toString(),
                org.getTaxNumber(),
                org.getLogoPath(),
                org.getIsVerified(),
                org.getVerificationCode(),
                org.getCreatedAt().toString()
        );
    }

    public Organization fetchOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + id));
    }
}
