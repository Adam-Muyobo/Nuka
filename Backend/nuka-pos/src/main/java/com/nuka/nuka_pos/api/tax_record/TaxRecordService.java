package com.nuka.nuka_pos.api.tax_record;

import com.nuka.nuka_pos.api.transaction.Transaction;
import com.nuka.nuka_pos.api.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TaxRecordService {

    private final TaxRecordRepository taxRecordRepository;
    private final TransactionService transactionService;

    @Autowired
    public TaxRecordService(TaxRecordRepository taxRecordRepository, TransactionService transactionService) {
        this.taxRecordRepository = taxRecordRepository;
        this.transactionService = transactionService;
    }

    // Create Tax Record
    @Transactional
    public TaxRecord createTaxRecord(Long transactionId, BigDecimal vatAmount, Boolean submittedToZRA) {
        Transaction transaction = transactionService.findById(transactionId);
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setTransaction(transaction);
        taxRecord.setVatAmount(vatAmount);
        taxRecord.setSubmittedToZRA(submittedToZRA);

        return taxRecordRepository.save(taxRecord);
    }

    // Update Tax Record
    @Transactional
    public TaxRecord updateTaxRecord(Long id, Long transactionId, BigDecimal vatAmount, Boolean submittedToZRA) {
        TaxRecord taxRecord = taxRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax record not found"));

        Transaction transaction = transactionService.findById(transactionId);

        taxRecord.setTransaction(transaction);
        taxRecord.setVatAmount(vatAmount);
        taxRecord.setSubmittedToZRA(submittedToZRA);

        return taxRecordRepository.save(taxRecord);
    }

    // Delete Tax Record
    @Transactional
    public void deleteTaxRecord(Long id) {
        TaxRecord taxRecord = taxRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax record not found"));

        taxRecordRepository.delete(taxRecord);
    }

    // Find Tax Record by ID
    public TaxRecord findById(Long id) {
        return taxRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax record not found"));
    }

    // Get All Tax Records
    public List<TaxRecord> findAll() {
        return taxRecordRepository.findAll();
    }

    // Get Tax Records by Transaction ID
    public List<TaxRecord> findByTransactionId(Long transactionId) {
        return taxRecordRepository.findByTransactionId(transactionId);
    }
}
