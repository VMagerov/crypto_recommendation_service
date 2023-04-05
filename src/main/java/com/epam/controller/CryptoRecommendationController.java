package com.epam.controller;

import com.epam.domain.CurrencyNormalizedView;
import com.epam.domain.CurrencyStatisticsView;
import com.epam.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CryptoRecommendationController {

    private final String DATE_FORMAT = "MM/dd/yyyy";
    @Autowired
    private final CryptoCurrencyService cryptoCurrencyService;

    public CryptoRecommendationController(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @GetMapping("/getByNormalizedDescending")
    public List<CurrencyNormalizedView> getByNormalizedDescending() {
       return cryptoCurrencyService.getCurrenciesListByNormalizedRangeDescending();
    }

    @GetMapping("/getCurrencyStatistics")
    public CurrencyStatisticsView getCurrencyStatistics(@RequestParam(name = "currencyName") String currencyName) {
        return cryptoCurrencyService.getCurrencyStatistics(currencyName);
    }

    @GetMapping("/getHighestNormalizedByRange")
    public CurrencyNormalizedView getHighestNormalizedByRange(@RequestParam(name = "start") @DateTimeFormat(pattern = DATE_FORMAT) LocalDate start,
                                                              @RequestParam(name = "end") @DateTimeFormat(pattern = DATE_FORMAT) LocalDate end) {
        return cryptoCurrencyService.getHighestNormalizedByRange(start, end);
    }

}
