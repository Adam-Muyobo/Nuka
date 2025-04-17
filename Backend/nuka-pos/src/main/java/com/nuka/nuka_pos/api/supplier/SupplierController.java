package com.nuka.nuka_pos.api.supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierResponse getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSupplier(@RequestBody Supplier supplier) {
        supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public void updateSupplier(@PathVariable Long id, @RequestBody Supplier updatedData) {
        supplierService.updateSupplier(id, updatedData);
    }

    @GetMapping("/organization/{orgId}")
    public List<SupplierResponse> getSuppliersByOrganization(@PathVariable("orgId") Long organizationId) {
        return supplierService.getSuppliersByOrganizationId(organizationId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
