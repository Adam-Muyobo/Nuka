package com.nuka.nuka_pos.api.taxtype;

import com.nuka.nuka_pos.api.taxtype.exceptions.TaxTypeAlreadyExistsException;
import com.nuka.nuka_pos.api.taxtype.exceptions.TaxTypeNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TaxTypeController handles HTTP requests related to tax types.
 * It provides endpoints for creating, updating, retrieving, and deleting tax types.
 */
@RestController
@RequestMapping("/api/secure/tax_types")
public class TaxTypeController {

    private final TaxTypeService taxTypeService;

    @Autowired
    public TaxTypeController(TaxTypeService taxTypeService) {
        this.taxTypeService = taxTypeService;
    }

    /**
     * Retrieves all tax types in the system.
     *
     * @return a list of tax types
     */
    @GetMapping
    public ResponseEntity<List<TaxTypeResponse>> getAllTaxTypes() {
        List<TaxType> taxTypes = taxTypeService.getAllTaxTypes();
        List<TaxTypeResponse> taxTypeResponses = taxTypes.stream()
                .map(taxType -> new TaxTypeResponse(taxType.getId(), taxType.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(taxTypeResponses, HttpStatus.OK);
    }

    /**
     * Retrieves a tax type by its ID.
     *
     * @param id the ID of the tax type
     * @return the tax type details
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxTypeResponse> getTaxTypeById(@PathVariable Long id) {
        TaxType taxType = taxTypeService.getTaxTypeById(id);
        TaxTypeResponse response = new TaxTypeResponse(taxType.getId(), taxType.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Creates a new tax type.
     *
     * @param taxType the tax type data to create
     * @return the created tax type
     */
    @PostMapping
    public ResponseEntity<TaxTypeResponse> createTaxType(@RequestBody @Valid TaxType taxType) {
        TaxType createdTaxType = taxTypeService.createTaxType(taxType);
        TaxTypeResponse response = new TaxTypeResponse(createdTaxType.getId(), createdTaxType.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an existing tax type.
     *
     * @param id the ID of the tax type to update
     * @param taxType the updated tax type data
     * @return the updated tax type
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxTypeResponse> updateTaxType(@PathVariable Long id, @RequestBody @Valid TaxType taxType) {
        TaxType updatedTaxType = taxTypeService.updateTaxType(id, taxType);
        TaxTypeResponse response = new TaxTypeResponse(updatedTaxType.getId(), updatedTaxType.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a tax type by its ID.
     *
     * @param id the ID of the tax type to delete
     * @return status response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxType(@PathVariable Long id) {
        taxTypeService.deleteTaxType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(TaxTypeNotFoundException.class)
    public ResponseEntity<String> handleTaxTypeNotFound(TaxTypeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaxTypeAlreadyExistsException.class)
    public ResponseEntity<String> handleTaxTypeAlreadyExists(TaxTypeAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
