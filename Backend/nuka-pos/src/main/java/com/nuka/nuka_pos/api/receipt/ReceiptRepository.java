package com.nuka.nuka_pos.api.receipt;

import com.nuka.nuka_pos.api.receipt.enums.ReceiptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // Filter by receipt type
    List<Receipt> findByType(ReceiptType type);
}
