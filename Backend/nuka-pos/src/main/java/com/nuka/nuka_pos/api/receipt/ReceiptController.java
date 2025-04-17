package com.nuka.nuka_pos.api.receipt;

import com.nuka.nuka_pos.api.receipt.enums.ReceiptType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping
    public List<ReceiptResponse> getAllReceipts() {
        return receiptService.getAllReceipts();
    }

    @GetMapping("/{id}")
    public ReceiptResponse getReceiptById(@PathVariable Long id) {
        return receiptService.getReceiptById(id);
    }

    @GetMapping("/type/{type}")
    public List<ReceiptResponse> getReceiptsByType(@PathVariable ReceiptType type) {
        return receiptService.getReceiptsByType(type);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReceipt(@RequestBody Receipt receipt) {
        receiptService.createReceipt(receipt);
    }

    @PutMapping("/{id}")
    public void updateReceipt(@PathVariable Long id, @RequestBody Receipt updatedData) {
        receiptService.updateReceipt(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReceipt(@PathVariable Long id) {
        receiptService.deleteReceipt(id);
    }
}
