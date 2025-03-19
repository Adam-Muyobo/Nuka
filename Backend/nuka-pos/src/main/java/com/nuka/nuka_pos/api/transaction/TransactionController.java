package com.nuka.nuka_pos.api.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(request.getUserId(), request.getTotalAmount(), request.getTaxAmount());
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.updateTransaction(id, request.getUserId(), request.getTotalAmount(), request.getTaxAmount());
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.findByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Transaction>> getTransactionsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Transaction> transactions = transactionService.findByDate(localDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
