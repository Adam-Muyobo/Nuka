package com.nuka.nuka_pos.api.sale;

import com.nuka.nuka_pos.api.sale.enums.SaleStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleResponse> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public SaleResponse getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSale(@RequestBody Sale sale) {
        saleService.createSale(sale);
    }

    @PutMapping("/{id}")
    public void updateSale(@PathVariable Long id, @RequestBody Sale updatedData) {
        saleService.updateSale(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }

    @GetMapping("/status/{status}")
    public List<SaleResponse> getSalesByStatus(@PathVariable SaleStatus status) {
        return saleService.getSalesByStatus(status);
    }

    @GetMapping("/branch/{branchId}")
    public List<SaleResponse> getSalesByBranchId(@PathVariable Long branchId) {
        return saleService.getSalesByBranchId(branchId);
    }

    @GetMapping("/customer/{customerId}")
    public List<SaleResponse> getSalesByCustomerId(@PathVariable Long customerId) {
        return saleService.getSalesByCustomerId(customerId);
    }
}
