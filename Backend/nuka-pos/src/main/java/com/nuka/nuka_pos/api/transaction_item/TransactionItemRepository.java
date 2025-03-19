package com.nuka.nuka_pos.api.transaction_item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
    List<TransactionItem> findAllByTransactionId(Long transactionId);

    List<TransactionItem> findByTransactionId(Long transactionId);
}
