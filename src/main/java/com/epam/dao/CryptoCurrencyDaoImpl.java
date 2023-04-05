package com.epam.dao;

import com.epam.domain.CryptoCurrency;
import com.epam.exception.CryptoServiceException;
import com.epam.properties.ApplicationProperties;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CryptoCurrencyDaoImpl implements CryptoCurrencyDao {

    @Autowired
    private final ApplicationProperties applicationProperties;

    private Map<String, List<CryptoCurrency>> currenciesMap = new HashMap<>();

    public CryptoCurrencyDaoImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Returns list of cryptocurrency info for the specified currency
     * @param currencyName name of a currency
     * @return List of all cryptocurrency info records related to the currency name specified
     */
    @Override
    public List<CryptoCurrency> getCurrencyData(String currencyName) {

        return currenciesMap.get(currencyName);
    }

    /**
     * Returns all names of currently loaded currencies
     * @return Set of all currency names
     */
    @Override
    public Set<String> getAllCurrencyNames() {
        return currenciesMap.keySet();
    }

    /**
     * Reloads all currency data from folder which was specified at properties as data storage
     */
    @Override
    public void reloadAllCurrencies() {
        Map<String, List<CryptoCurrency>> newCurrenciesMap = new HashMap<>();
        File files = Paths.get(applicationProperties.getLocation()).toFile();
        for (File file: files.listFiles()) {
            String currencyName = StringUtils.substringBefore(file.getName(), applicationProperties.getPostfix());
            try {
                List<CryptoCurrency> data = new CsvToBeanBuilder(new FileReader(file.getAbsoluteFile()))
                        .withType(CryptoCurrency.class)
                        .build()
                        .parse();

                newCurrenciesMap.put(currencyName, data);
            } catch (FileNotFoundException e) {
                throw new CryptoServiceException("Failed to load data for currency: " + currencyName);
            }
        }
        currenciesMap = newCurrenciesMap;
    }

    @PostConstruct
    private void initCurrencies() {
        reloadAllCurrencies();
    }
}
