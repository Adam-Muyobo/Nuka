package com.nuka.nuka_pos.api.transaction_item;

import com.nuka.nuka_pos.api.product.Product;
import com.nuka.nuka_pos.api.product.ProductRepository;
import com.nuka.nuka_pos.api.transaction.Transaction;
import com.nuka.nuka_pos.api.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionItemService {

    private final TransactionItemRepository transactionItemRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TransactionItemService(TransactionItemRepository transactionItemRepository,
                                  TransactionRepository transactionRepository,
                                  ProductRepository productRepository) {
        this.transactionItemRepository = transactionItemRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    // Add Transaction Item
    public TransactionItem addTransactionItem(Long transactionId, Long productId, Integer quantity) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setTransaction(transaction);
        transactionItem.setProduct(product);
        transactionItem.setQuantity(quantity);
        transactionItem.setPrice(BigDecimal.valueOf(product.getPrice()));

        return transactionItemRepository.save(transactionItem);
    }

    // Update Transaction Item
    public TransactionItem updateTransactionItem(Long id, TransactionItemRequest request) {
        TransactionItem transactionItem = transactionItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Item not found"));

        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        transactionItem.setTransaction(transaction);
        transactionItem.setProduct(product);
        transactionItem.setQuantity(request.getQuantity());
        transactionItem.setPrice(BigDecimal.valueOf(product.getPrice()));

        return transactionItemRepository.save(transactionItem);
    }

    // Delete Transaction Item
    public void deleteTransactionItem(Long id) {
        TransactionItem transactionItem = transactionItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Item not found"));
        transactionItemRepository.delete(transactionItem);
    }

    // Find Transaction Item by ID
    public TransactionItem findById(Long id) {
        return transactionItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Item not found"));
    }

    // Get All Transaction Items
    public List<TransactionItem> findAll() {
        return transactionItemRepository.findAll();
    }

    // Get All Transaction Items for a Specific Transaction
    public List<TransactionItem> findByTransactionId(Long transactionId) {
        return transactionItemRepository.findByTransactionId(transactionId);
    }
}
