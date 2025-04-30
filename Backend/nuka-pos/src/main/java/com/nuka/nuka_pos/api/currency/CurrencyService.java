package com.nuka.nuka_pos.api.currency;

import com.nuka.nuka_pos.api.currency.exceptions.CurrencyAlreadyExistsException;
import com.nuka.nuka_pos.api.currency.exceptions.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency createCurrency(Currency currency) {
        if (currencyRepository.existsByName(currency.getName())) {
            throw new CurrencyAlreadyExistsException("Currency with name " + currency.getName() + " already exists.");
        }
        return currencyRepository.save(currency);
    }

    public List<Currency> createMultipleCurrencies(List<Currency> currencies) {
        for (Currency currency : currencies) {
            if (currencyRepository.existsByName(currency.getName())) {
                throw new CurrencyAlreadyExistsException("Currency with name " + currency.getName() + " already exists.");
            }
        }
        return currencyRepository.saveAll(currencies);
    }

    public Currency updateCurrency(Long id, Currency currency) {
        Currency existingCurrency = currencyRepository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency with ID " + id + " not found"));
        existingCurrency.setName(currency.getName());
        existingCurrency.setSymbol(currency.getSymbol());
        existingCurrency.setCountry(currency.getCountry());
        return currencyRepository.save(existingCurrency);
    }

    public void deleteCurrency(Long id) {
        if (!currencyRepository.existsById(id)) {
            throw new CurrencyNotFoundException("Currency with ID " + id + " not found");
        }
        currencyRepository.deleteById(id);
    }

    public List<Currency> getCurrenciesByCountry(String country) {
        return currencyRepository.findByCountryIgnoreCase(country);
    }
}
