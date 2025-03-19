package com.nuka.nuka_pos.api.transaction_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-items")
public class TransactionItemController {

    private final TransactionItemService transactionItemService;

    @Autowired
    public TransactionItemController(TransactionItemService transactionItemService) {
        this.transactionItemService = transactionItemService;
    }

    // Add Transaction Item
    @PostMapping
    public ResponseEntity<TransactionItem> addTransactionItem(@RequestBody TransactionItemRequest request) {
        TransactionItem createdTransactionItem = transactionItemService.addTransactionItem(
                request.getTransactionId(), request.getProductId(), request.getQuantity()
        );
        return new ResponseEntity<>(createdTransactionItem, HttpStatus.CREATED);
    }

    // Update Transaction Item
    @PutMapping("/{id}")
    public ResponseEntity<TransactionItem> updateTransactionItem(@PathVariable Long id, @RequestBody TransactionItemRequest request) {
        TransactionItem updatedTransactionItem = transactionItemService.updateTransactionItem(id, request);
        return new ResponseEntity<>(updatedTransactionItem, HttpStatus.OK);
    }

    // Delete Transaction Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionItem(@PathVariable Long id) {
        transactionItemService.deleteTransactionItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get Transaction Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionItem> getTransactionItemById(@PathVariable Long id) {
        TransactionItem transactionItem = transactionItemService.findById(id);
        return new ResponseEntity<>(transactionItem, HttpStatus.OK);
    }

    // Get All Transaction Items
    @GetMapping
    public ResponseEntity<List<TransactionItem>> getAllTransactionItems() {
        List<TransactionItem> transactionItems = transactionItemService.findAll();
        return new ResponseEntity<>(transactionItems, HttpStatus.OK);
    }

    // Get Transaction Items by Transaction ID
    @GetMapping("/by-transaction/{transactionId}")
    public ResponseEntity<List<TransactionItem>> getTransactionItemsByTransactionId(@PathVariable Long transactionId) {
        List<TransactionItem> transactionItems = transactionItemService.findByTransactionId(transactionId);
        return new ResponseEntity<>(transactionItems, HttpStatus.OK);
    }
}
