package com.nuka.nuka_pos.api.taxtype;

import com.nuka.nuka_pos.api.taxtype.exceptions.TaxTypeNotFoundException;
import com.nuka.nuka_pos.api.taxtype.exceptions.TaxTypeAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TaxTypeService provides business logic for managing tax types.
 */
@Service
public class TaxTypeService {

    private final TaxTypeRepository taxTypeRepository;

    @Autowired
    public TaxTypeService(TaxTypeRepository taxTypeRepository) {
        this.taxTypeRepository = taxTypeRepository;
    }

    /**
     * Retrieves all tax types in the system.
     *
     * @return a list of all tax types
     */
    public List<TaxType> getAllTaxTypes() {
        return taxTypeRepository.findAll();
    }

    /**
     * Retrieves a tax type by its ID.
     *
     * @param id the ID of the tax type
     * @return the tax type if found
     */
    public TaxType getTaxTypeById(Long id) {
        return taxTypeRepository.findById(id)
                .orElseThrow(() -> new TaxTypeNotFoundException("TaxType with ID " + id + " not found"));
    }

    /**
     * Creates a new tax type.
     *
     * @param taxType the tax type to create
     * @return the created tax type
     */
    public TaxType createTaxType(TaxType taxType) {
        if (taxTypeRepository.existsByName(taxType.getName())) {
            throw new TaxTypeAlreadyExistsException("TaxType with name " + taxType.getName() + " already exists");
        }
        return taxTypeRepository.save(taxType);
    }

    /**
     * Updates an existing tax type.
     *
     * @param id the ID of the tax type to update
     * @param taxType the updated tax type data
     * @return the updated tax type
     */
    public TaxType updateTaxType(Long id, TaxType taxType) {
        TaxType existingTaxType = getTaxTypeById(id);
        existingTaxType.setName(taxType.getName());
        return taxTypeRepository.save(existingTaxType);
    }

    /**
     * Deletes a tax type by its ID.
     *
     * @param id the ID of the tax type to delete
     */
    public void deleteTaxType(Long id) {
        taxTypeRepository.deleteById(id);
    }
}
