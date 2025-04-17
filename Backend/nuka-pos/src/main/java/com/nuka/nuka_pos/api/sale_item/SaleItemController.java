package com.nuka.nuka_pos.api.sale_item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-items")
public class SaleItemController {

    private final SaleItemService saleItemService;

    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @GetMapping
    public List<SaleItemResponse> getAllSaleItems() {
        return saleItemService.getAllSaleItems();
    }

    @GetMapping("/{id}")
    public SaleItemResponse getSaleItemById(@PathVariable Long id) {
        return saleItemService.getSaleItemById(id);
    }

    @GetMapping("/by-sale/{saleId}")
    public List<SaleItemResponse> getSaleItemsBySaleId(@PathVariable Long saleId) {
        return saleItemService.getSaleItemsBySaleId(saleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaleItem(@RequestBody SaleItem saleItem) {
        saleItemService.createSaleItem(saleItem);
    }

    @PutMapping("/{id}")
    public void updateSaleItem(@PathVariable Long id, @RequestBody SaleItem saleItem) {
        saleItemService.updateSaleItem(id, saleItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSaleItem(@PathVariable Long id) {
        saleItemService.deleteSaleItem(id);
    }
}
