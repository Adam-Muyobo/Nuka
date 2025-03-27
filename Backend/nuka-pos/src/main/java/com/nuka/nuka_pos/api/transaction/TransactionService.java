package com.nuka.nuka_pos.api.transaction;

import com.nuka.nuka_pos.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Transactional
    public Transaction createTransaction(Long userId, BigDecimal totalAmount, BigDecimal taxAmount) {
        var user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTotalAmount(totalAmount);
        transaction.setTaxAmount(taxAmount);
        transaction.setTransactionDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Long id, Long userId, BigDecimal totalAmount, BigDecimal taxAmount) {
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        var user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        transaction.setUser(user);
        transaction.setTotalAmount(totalAmount);
        transaction.setTaxAmount(taxAmount);
        transaction.setTransactionDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        transactionRepository.delete(transaction);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> findByDate(LocalDate date) {
        return transactionRepository.findByTransactionDate(date);
    }
}
