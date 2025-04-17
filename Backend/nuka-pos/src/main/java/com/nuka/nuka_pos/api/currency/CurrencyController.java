package com.nuka.nuka_pos.api.currency;

import com.nuka.nuka_pos.api.currency.exceptions.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CurrencyController handles HTTP requests related to currencies.
 * It provides endpoints for creating, updating, retrieving, and deleting currencies.
 */
@RestController
@RequestMapping("/api/secure/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Retrieves all currencies in the system.
     *
     * @return a list of currencies
     */
    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        List<CurrencyResponse> currencyResponses = currencies.stream()
                .map(currency -> new CurrencyResponse(currency.getId(), currency.getName(), currency.getSymbol()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(currencyResponses, HttpStatus.OK);
    }

    /**
     * Retrieves a currency by its ID.
     *
     * @param id the ID of the currency
     * @return the currency details
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getCurrencyById(@PathVariable Long id) {
        Currency currency = currencyService.getAllCurrencies()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));

        CurrencyResponse response = new CurrencyResponse(currency.getId(), currency.getName(), currency.getSymbol());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Creates a new currency.
     *
     * @param currency the currency data to create
     * @return the created currency
     */
    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(@RequestBody Currency currency) {
        Currency createdCurrency = currencyService.createCurrency(currency);
        CurrencyResponse response = new CurrencyResponse(createdCurrency.getId(), createdCurrency.getName(), createdCurrency.getSymbol());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an existing currency.
     *
     * @param id the ID of the currency to update
     * @param currency the updated currency data
     * @return the updated currency
     */
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        Currency updatedCurrency = currencyService.updateCurrency(id, currency);
        CurrencyResponse response = new CurrencyResponse(updatedCurrency.getId(), updatedCurrency.getName(), updatedCurrency.getSymbol());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a currency by its ID.
     *
     * @param id the ID of the currency to delete
     * @return status response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
