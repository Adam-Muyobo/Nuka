package com.nuka.nuka_pos.api.tax_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax-records")
public class TaxRecordController {

    private final TaxRecordService taxRecordService;

    @Autowired
    public TaxRecordController(TaxRecordService taxRecordService) {
        this.taxRecordService = taxRecordService;
    }

    // 1. Create Tax Record
    @PostMapping
    public ResponseEntity<TaxRecordResponse> createTaxRecord(
            @RequestBody TaxRecordResponse taxRecordRequestDTO) {

        TaxRecord taxRecord = taxRecordService.createTaxRecord(
                taxRecordRequestDTO.getTransactionId(),
                taxRecordRequestDTO.getVatAmount(),
                taxRecordRequestDTO.getSubmittedToZRA()
        );

        TaxRecordResponse responseDTO = new TaxRecordResponse(
                taxRecord.getId(),
                taxRecord.getTransaction().getId(),
                taxRecord.getVatAmount(),
                taxRecord.getSubmittedToZRA()
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // 2. Update Tax Record
    @PutMapping("/{id}")
    public ResponseEntity<TaxRecordResponse> updateTaxRecord(
            @PathVariable Long id,
            @RequestBody TaxRecordResponse taxRecordRequestDTO) {

        TaxRecord taxRecord = taxRecordService.updateTaxRecord(
                id,
                taxRecordRequestDTO.getTransactionId(),
                taxRecordRequestDTO.getVatAmount(),
                taxRecordRequestDTO.getSubmittedToZRA()
        );

        TaxRecordResponse responseDTO = new TaxRecordResponse(
                taxRecord.getId(),
                taxRecord.getTransaction().getId(),
                taxRecord.getVatAmount(),
                taxRecord.getSubmittedToZRA()
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 3. Delete Tax Record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxRecord(@PathVariable Long id) {
        taxRecordService.deleteTaxRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 4. Get Tax Record by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaxRecordResponse> getTaxRecordById(@PathVariable Long id) {
        TaxRecord taxRecord = taxRecordService.findById(id);
        TaxRecordResponse responseDTO = new TaxRecordResponse(
                taxRecord.getId(),
                taxRecord.getTransaction().getId(),
                taxRecord.getVatAmount(),
                taxRecord.getSubmittedToZRA()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 5. Get All Tax Records
    @GetMapping
    public ResponseEntity<List<TaxRecordResponse>> getAllTaxRecords() {
        List<TaxRecord> taxRecords = taxRecordService.findAll();
        List<TaxRecordResponse> responseDTOs = taxRecords.stream().map(taxRecord -> new TaxRecordResponse(
                taxRecord.getId(),
                taxRecord.getTransaction().getId(),
                taxRecord.getVatAmount(),
                taxRecord.getSubmittedToZRA()
        )).toList();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // 6. Get Tax Records by Transaction ID
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<TaxRecordResponse>> getTaxRecordsByTransactionId(@PathVariable Long transactionId) {
        List<TaxRecord> taxRecords = taxRecordService.findByTransactionId(transactionId);
        List<TaxRecordResponse> responseDTOs = taxRecords.stream().map(taxRecord -> new TaxRecordResponse(
                taxRecord.getId(),
                taxRecord.getTransaction().getId(),
                taxRecord.getVatAmount(),
                taxRecord.getSubmittedToZRA()
        )).toList();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
}

