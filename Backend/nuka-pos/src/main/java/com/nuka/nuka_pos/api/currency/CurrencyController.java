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
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        List<CurrencyResponse> responses = currencies.stream()
                .map(c -> new CurrencyResponse(c.getId(), c.getName(), c.getSymbol(), c.getCountry()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getCurrencyById(@PathVariable Long id) {
        Currency currency = currencyService.getAllCurrencies().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));

        CurrencyResponse response = new CurrencyResponse(currency.getId(), currency.getName(), currency.getSymbol(), currency.getCountry());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(@RequestBody Currency currency) {
        Currency created = currencyService.createCurrency(currency);
        CurrencyResponse response = new CurrencyResponse(created.getId(), created.getName(), created.getSymbol(), created.getCountry());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<CurrencyResponse>> createCurrencies(@RequestBody List<Currency> currencies) {
        List<Currency> createdList = currencyService.createMultipleCurrencies(currencies);
        List<CurrencyResponse> responses = createdList.stream()
                .map(c -> new CurrencyResponse(c.getId(), c.getName(), c.getSymbol(), c.getCountry()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        Currency updated = currencyService.updateCurrency(id, currency);
        CurrencyResponse response = new CurrencyResponse(updated.getId(), updated.getName(), updated.getSymbol(), updated.getCountry());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-country/{country}")
    public ResponseEntity<List<CurrencyResponse>> getCurrenciesByCountry(@PathVariable String country) {
        List<Currency> currencies = currencyService.getCurrenciesByCountry(country);
        List<CurrencyResponse> responses = currencies.stream()
                .map(c -> new CurrencyResponse(c.getId(), c.getName(), c.getSymbol(), c.getCountry()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}

