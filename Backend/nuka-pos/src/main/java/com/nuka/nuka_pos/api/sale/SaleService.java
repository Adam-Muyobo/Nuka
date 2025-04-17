package com.nuka.nuka_pos.api.sale;

import com.nuka.nuka_pos.api.sale.enums.SaleStatus;
import com.nuka.nuka_pos.api.sale.exceptions.SaleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found with id: " + id));
        return mapToResponse(sale);
    }

    public void createSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void updateSale(Long id, Sale updatedData) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found with id: " + id));

        if (updatedData.getOrganization() != null) existingSale.setOrganization(updatedData.getOrganization());
        if (updatedData.getBranch() != null) existingSale.setBranch(updatedData.getBranch());
        if (updatedData.getCustomer() != null) existingSale.setCustomer(updatedData.getCustomer());
        if (updatedData.getUser() != null) existingSale.setUser(updatedData.getUser());
        if (updatedData.getSaleDate() != null) existingSale.setSaleDate(updatedData.getSaleDate());
        if (updatedData.getIsPaid() != null) existingSale.setIsPaid(updatedData.getIsPaid());
        if (updatedData.getStatus() != null) existingSale.setStatus(updatedData.getStatus());

        saleRepository.save(existingSale);
    }

    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException("Sale not found with id: " + id);
        }
        saleRepository.deleteById(id);
    }

    public List<SaleResponse> getSalesByStatus(SaleStatus status) {
        return saleRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> getSalesByBranchId(Long branchId) {
        return saleRepository.findByBranchId(branchId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> getSalesByCustomerId(Long customerId) {
        return saleRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SaleResponse mapToResponse(Sale sale) {
        return new SaleResponse(
                sale.getId(),
                sale.getOrganization().getId(),
                sale.getBranch().getId(),
                sale.getCustomer().getId(),
                sale.getUser().getId(),
                sale.getSaleDate(),
                sale.getIsPaid(),
                sale.getStatus().name()
        );
    }
}
