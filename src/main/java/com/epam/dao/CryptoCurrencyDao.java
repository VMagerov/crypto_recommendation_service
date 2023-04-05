package com.epam.dao;

import com.epam.domain.CryptoCurrency;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface CryptoCurrencyDao {

    /**
     * Returns list of cryptocurrency info for the specified currency
     * @param currencyName name of a currency
     * @return List of all cryptocurrency info records related to the currency name specified
     */
    List<CryptoCurrency> getCurrencyData(String currencyName);

    /**
     * Reloads all currency data from folder which was specified at properties as data storage
     */
    void reloadAllCurrencies();

    /**
     * Returns all names of currently loaded currencies
     * @return Set of all currency names
     */
    Set<String> getAllCurrencyNames();
}
