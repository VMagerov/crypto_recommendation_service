package com.epam.service;

import com.epam.domain.CryptoCurrency;
import com.epam.domain.CurrencyNormalizedView;
import com.epam.domain.CurrencyStatisticsView;

import java.time.LocalDate;
import java.util.List;

public interface CryptoCurrencyService {

    /**
     * Returns list of cryptocurrency info for the specified currency
     * @param currencyName name of a currency
     * @return List of all cryptocurrency info records related to the currency name specified
     */
    List<CryptoCurrency> getCurrencyData(String currencyName);

    /**
     * Returns max value for specified currency for the whole period of time
     * @param currencyName
     * @return Double value of max calculated
     */
    Double getMax(String currencyName);

    /**
     * Returns max value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of max calculated
     */
    Double getMax(String currencyName, LocalDate start, LocalDate end);

    /**
     * Returns min value for specified currency for the whole period of time
     * @param currencyName
     * @return Double value of min calculated
     */
    Double getMin(String currencyName);

    /**
     * Returns min value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of min calculated
     */
    Double getMin(String currencyName, LocalDate start, LocalDate end);

    /**
     * Returns the oldest value for specified currency for the whole period of time
     * @param currencyName
     * @return the oldest value as Double
     */
    Double getOldest(String currencyName);

    /**
     * Returns the newest value for specified currency for the whole period of time
     * @param currencyName
     * @return the newest value as Double
     */
    Double getNewest(String currencyName);

    /**
     * Returns normalized range value for specified currency for the whole period of time
     * @param currencyName currency name
     * @return Double value of normalized range calculated
     */
    Double getNormalizedRange(String currencyName);

    /**
     * Returns normalized range value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of normalized range calculated
     */
    Double getNormalizedRange(String currencyName, LocalDate start, LocalDate end);

    /**
     * Returns a list of CurrencyNormalizedView objects containing currency name
     * and normalized range value for the whole period of time in descending order by normalized range value.
     * @return descending by normalized range value list of currencies
     */
    List<CurrencyNormalizedView> getCurrenciesListByNormalizedRangeDescending();

    /**
     * Returns CurrencyNormalizedView object containing currency with highest normalized range value by date range specified
     * @param start start of date range
     * @param end end of date range
     * @return currency with highest normalized range value
     */
    CurrencyNormalizedView getHighestNormalizedByRange(LocalDate start, LocalDate end);

    /**
     * Returns an CurrencyStatisticsView object containing oldest, newest,
     * min and max values for specified currency for the whole period of time.
     * @param currencyName a currency name to get statistics for
     * @return CurrencyStatisticsView containing oldest, newest, min and max values
     */
    CurrencyStatisticsView getCurrencyStatistics(String currencyName);
}
