package com.epam.service;

import com.epam.dao.CryptoCurrencyDao;
import com.epam.domain.CryptoCurrency;
import com.epam.domain.CurrencyNormalizedView;
import com.epam.domain.CurrencyStatisticsView;
import com.epam.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    @Autowired
    private final CryptoCurrencyDao cryptoCurrencyDao;

    public CryptoCurrencyServiceImpl(CryptoCurrencyDao cryptoCurrencyDao) {
        this.cryptoCurrencyDao = cryptoCurrencyDao;
    }

    /**
     * Returns list of cryptocurrency info for the specified currency
     * @param currencyName name of a currency
     * @return List of all cryptocurrency info records related to the currency name specified
     */
    @Override
    public List<CryptoCurrency> getCurrencyData(String currencyName) {
        List<CryptoCurrency> data = cryptoCurrencyDao.getCurrencyData(currencyName);
        if (data == null) {
            throw new BadRequestException("This currency is not supported at the moment.");
        }
        return data;
    }

    /**
     * Returns the oldest value for specified currency for the whole period of time
     * @param currencyName
     * @return the oldest value as Double
     */
    @Override
    public Double getOldest(String currencyName) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        Optional<CryptoCurrency> oldestOptional = data.stream()
                .min(Comparator.comparing(CryptoCurrency::getTimestamp));

        CryptoCurrency oldest = oldestOptional.get();
        Double oldestPrice= oldest.getPrice();
        return oldestPrice;
    }

    /**
     * Returns the newest value for specified currency for the whole period of time
     * @param currencyName
     * @return the newest value as Double
     */
    @Override
    public Double getNewest(String currencyName) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        Optional<CryptoCurrency> oldestOptional = data.stream()
                .max(Comparator.comparing(CryptoCurrency::getTimestamp));

        CryptoCurrency newest = oldestOptional.get();
        Double newestPrice= newest.getPrice();
        return newestPrice;
    }

    /**
     * Returns min value for specified currency for the whole period of time
     * @param currencyName
     * @return Double value of min calculated
     */
    @Override
    public Double getMin(String currencyName) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        OptionalDouble min = data.stream()
                .mapToDouble(x -> x.getPrice())
                .min();

        return min.getAsDouble();
    }

    /**
     * Returns min value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of min calculated
     */
    @Override
    public Double getMin(String currencyName, LocalDate start, LocalDate end) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        OptionalDouble min = data.stream()
                .filter(x -> {
                    LocalDate date = x.getTimestamp().toLocalDate();
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .mapToDouble(x -> x.getPrice())
                .min();

        return min.getAsDouble();
    }

    /**
     * Returns max value for specified currency for the whole period of time
     * @param currencyName
     * @return Double value of max calculated
     */
    @Override
    public Double getMax(String currencyName) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        OptionalDouble max = data.stream()
                .mapToDouble(x -> x.getPrice())
                .max();

        return max.getAsDouble();
    }

    /**
     * Returns max value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of max calculated
     */
    @Override
    public Double getMax(String currencyName, LocalDate start, LocalDate end) {
        List<CryptoCurrency> data = getCurrencyData(currencyName);
        OptionalDouble max = data.stream()
                .filter(x -> {
                    LocalDate date = x.getTimestamp().toLocalDate();
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .mapToDouble(x -> x.getPrice())
                .max();

        return max.getAsDouble();
    }

    /**
     * Returns normalized range value for specified currency for the whole period of time
     * @param currencyName currency name
     * @return Double value of normalized range calculated
     */
    @Override
    public Double getNormalizedRange(String currencyName) {
        Double min = getMin(currencyName);
        Double max = getMax(currencyName);
        Double normalizedRange = (max-min)/min;
        return normalizedRange;
    }

    /**
     * Returns normalized range value for specified currency for specified date range
     * @param currencyName currency name
     * @param start start of date range
     * @param end end of date range
     * @return Double value of normalized range calculated
     */
    @Override
    public Double getNormalizedRange(String currencyName, LocalDate start, LocalDate end) {
        Double min = getMin(currencyName, start, end);
        Double max = getMax(currencyName, start, end);
        Double normalizedRange = (max-min)/min;
        return normalizedRange;
    }

    /**
     * Returns CurrencyNormalizedView object containing currency with highest normalized range value by date range specified
     * @param start start of date range
     * @param end end of date range
     * @return currency with highest normalized range value
     */
    @Override
    public CurrencyNormalizedView getHighestNormalizedByRange(LocalDate start, LocalDate end) {
        Set<String> currencyNames = cryptoCurrencyDao.getAllCurrencyNames();
        List<CurrencyNormalizedView> currencyNormalizedViews = new ArrayList<>();
        for (String currencyName : currencyNames) {
            Double normalizedRange = getNormalizedRange(currencyName, start, end);
            CurrencyNormalizedView currencyNormalizedView = new CurrencyNormalizedView(currencyName, normalizedRange);
            currencyNormalizedViews.add(currencyNormalizedView);
        }
        Optional<CurrencyNormalizedView> highestNormalized = currencyNormalizedViews.stream()
                .max(Comparator.comparing(CurrencyNormalizedView::getNormalizedValue));

        return highestNormalized.get();
    }

    /**
     * Returns a list of CurrencyNormalizedView objects containing currency name
     * and normalized range value for the whole period of time in descending order by normalized range value.
     * @return descending by normalized range value list of currencies
     */
    @Override
    public List<CurrencyNormalizedView> getCurrenciesListByNormalizedRangeDescending() {
        Set<String> currencyNames = cryptoCurrencyDao.getAllCurrencyNames();
        List<CurrencyNormalizedView> currencyNormalizedViews = new ArrayList<>();
        for (String currencyName : currencyNames) {
            Double normalizedRange = getNormalizedRange(currencyName);
            CurrencyNormalizedView currencyNormalizedView = new CurrencyNormalizedView(currencyName, normalizedRange);
            currencyNormalizedViews.add(currencyNormalizedView);
        }
        currencyNormalizedViews = currencyNormalizedViews.stream()
                .sorted(Comparator.comparing(CurrencyNormalizedView::getNormalizedValue).reversed())
                .collect(Collectors.toList());

        return currencyNormalizedViews;
    }

    /**
     * Returns an CurrencyStatisticsView object containing oldest, newest,
     * min and max values for specified currency for the whole period of time.
     * @param currencyName a currency name to get statistics for
     * @return CurrencyStatisticsView containing oldest, newest, min and max values
     */
    @Override
    public CurrencyStatisticsView getCurrencyStatistics(String currencyName) {
        Double oldest = getOldest(currencyName);
        Double newest = getNewest(currencyName);
        Double min = getMin(currencyName);
        Double max = getMax(currencyName);
        CurrencyStatisticsView currencyStatistics = new CurrencyStatisticsView(currencyName, oldest, newest, min, max);

        return currencyStatistics;
    }
}
