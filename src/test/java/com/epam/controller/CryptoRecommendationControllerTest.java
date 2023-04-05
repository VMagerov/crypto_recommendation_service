package com.epam.controller;

import com.epam.domain.CurrencyNormalizedView;
import com.epam.domain.CurrencyStatisticsView;
import com.epam.service.CryptoCurrencyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CryptoRecommendationControllerTest {

    @Spy
    @InjectMocks
    private CryptoRecommendationController cryptoRecommendationController;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;

    @Test
    public void getByNormalizedDescending_cryptoServiceReturnedData_resultReturned() {

        CurrencyNormalizedView currencyNormalizedView = Mockito.mock(CurrencyNormalizedView.class);
        List<CurrencyNormalizedView> expected = Collections.singletonList(currencyNormalizedView);
        Mockito.when(cryptoCurrencyService.getCurrenciesListByNormalizedRangeDescending()).thenReturn(expected);

        List<CurrencyNormalizedView> actual = cryptoRecommendationController.getByNormalizedDescending();

        Mockito.verify(cryptoCurrencyService).getCurrenciesListByNormalizedRangeDescending();
        Mockito.verifyNoMoreInteractions(cryptoCurrencyService);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCurrencyStatistics_cryptoServiceReturnedData_resultReturned() {

        String currencyName = "SHREK";
        Double expectedMax = 0.45;
        Double expectedMin = 0.4;
        Double expectedOldest = 0.4365;
        Double expectedNewest = 0.2346;
        CurrencyStatisticsView expected = Mockito.mock(CurrencyStatisticsView.class);
        Mockito.when(expected.getMax()).thenReturn(expectedMax);
        Mockito.when(expected.getMin()).thenReturn(expectedMin);
        Mockito.when(expected.getOldest()).thenReturn(expectedOldest);
        Mockito.when(expected.getNewest()).thenReturn(expectedNewest);
        Mockito.when(cryptoCurrencyService.getCurrencyStatistics(currencyName)).thenReturn(expected);

        CurrencyStatisticsView actual = cryptoRecommendationController.getCurrencyStatistics(currencyName);

        Mockito.verify(cryptoCurrencyService).getCurrencyStatistics(currencyName);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyService);
        Assert.assertEquals(expected.getMax(), actual.getMax());
        Assert.assertEquals(expected.getMin(), actual.getMin());
        Assert.assertEquals(expected.getNewest(), actual.getNewest());
        Assert.assertEquals(expected.getOldest(), actual.getOldest());
    }

    @Test
    public void getHighestNormalizedByRange_cryptoServiceReturnedData_resultReturned() {

        String currencyName = "SHREK";
        Double expectedNormalizedValue = 0.45;
        CurrencyNormalizedView expected = Mockito.mock(CurrencyNormalizedView.class);
        Mockito.when(expected.getName()).thenReturn(currencyName);
        Mockito.when(expected.getNormalizedValue()).thenReturn(expectedNormalizedValue);
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        Mockito.when(cryptoCurrencyService.getHighestNormalizedByRange(start, end)).thenReturn(expected);

        CurrencyNormalizedView actual = cryptoRecommendationController.getHighestNormalizedByRange(start, end);

        Mockito.verify(cryptoCurrencyService).getHighestNormalizedByRange(start, end);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyService);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getNormalizedValue(), actual.getNormalizedValue());
    }
}
