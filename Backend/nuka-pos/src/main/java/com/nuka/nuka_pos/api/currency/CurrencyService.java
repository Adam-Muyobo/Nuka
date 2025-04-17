package com.nuka.nuka_pos.api.currency;

import com.nuka.nuka_pos.api.currency.exceptions.CurrencyAlreadyExistsException;
import com.nuka.nuka_pos.api.currency.exceptions.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CurrencyService provides business logic for managing currencies.
 * It includes methods to create, update, delete, and retrieve currencies.
 */
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Retrieves all currencies.
     *
     * @return a list of all currencies
     */
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    /**
     * Creates a new currency.
     *
     * @param currency the currency to create
     * @return the created currency
     * @throws CurrencyAlreadyExistsException if the currency already exists
     */
    public Currency createCurrency(Currency currency) {
        if (currencyRepository.existsByName(currency.getName())) {
            throw new CurrencyAlreadyExistsException("Currency with name " + currency.getName() + " already exists.");
        }
        return currencyRepository.save(currency);
    }

    /**
     * Updates an existing currency.
     *
     * @param id the ID of the currency to update
     * @param currency the updated currency data
     * @return the updated currency
     * @throws CurrencyNotFoundException if the currency is not found
     */
    public Currency updateCurrency(Long id, Currency currency) {
        Currency existingCurrency = currencyRepository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency with ID " + id + " not found"));
        existingCurrency.setName(currency.getName());
        existingCurrency.setSymbol(currency.getSymbol());
        return currencyRepository.save(existingCurrency);
    }

    /**
     * Deletes a currency by its ID.
     *
     * @param id the ID of the currency to delete
     * @throws CurrencyNotFoundException if the currency is not found
     */
    public void deleteCurrency(Long id) {
        if (!currencyRepository.existsById(id)) {
            throw new CurrencyNotFoundException("Currency with ID " + id + " not found");
        }
        currencyRepository.deleteById(id);
    }
}
