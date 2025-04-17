package com.nuka.nuka_pos.api.supplier;

import com.nuka.nuka_pos.api.supplier.exceptions.SupplierNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));
        return mapToResponse(supplier);
    }

    public void createSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Long id, Supplier updatedData) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));

        if (updatedData.getName() != null) existingSupplier.setName(updatedData.getName());
        if (updatedData.getPhone() != null) existingSupplier.setPhone(updatedData.getPhone());
        if (updatedData.getEmail() != null) existingSupplier.setEmail(updatedData.getEmail());
        if (updatedData.getTaxNumber() != null) existingSupplier.setTaxNumber(updatedData.getTaxNumber());
        if (updatedData.getIsActive() != null) existingSupplier.setIsActive(updatedData.getIsActive());
        if (updatedData.getOrganization() != null) existingSupplier.setOrganization(updatedData.getOrganization());

        supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
    public List<SupplierResponse> getSuppliersByOrganizationId(Long organizationId) {
        return supplierRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private SupplierResponse mapToResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getTaxNumber(),
                supplier.getIsActive(),
                supplier.getOrganization().getId()
        );
    }
}
