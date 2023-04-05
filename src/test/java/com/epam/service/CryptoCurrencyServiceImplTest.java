package com.epam.service;

import com.epam.dao.CryptoCurrencyDao;
import com.epam.domain.CryptoCurrency;
import com.epam.domain.CurrencyNormalizedView;
import com.epam.domain.CurrencyStatisticsView;
import com.epam.exception.BadRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class CryptoCurrencyServiceImplTest {

    @Spy
    @InjectMocks
    private CryptoCurrencyServiceImpl cryptoCurrencyService;

    @Mock
    private CryptoCurrencyDao cryptoCurrencyDao;


    private final String currencyShrek = "SHREK";
    private final Double yesterdayShrekPrice = 0.423;
    private final Double todayShrekPrice = 0.999;
    private final Double tomorrowShrekPrice = 0.678;

    private List<CryptoCurrency> cryptoCurrenciesShrek;

    private final String currencyMeow = "MEOW";
    private final Double yesterdayMeowPrice = 12.423;
    private final Double todayMeowPrice = 56.999;
    private final Double tomorrowMeowPrice = 34.678;

    private List<CryptoCurrency> cryptoCurrenciesMeow;
    @Before
    public void setUp() {
        CryptoCurrency cryptoCurrencyShrekYesterday = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyShrekYesterday.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(1));
        Mockito.when(cryptoCurrencyShrekYesterday.getPrice()).thenReturn(yesterdayShrekPrice);
        CryptoCurrency cryptoCurrencyShrekToday = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyShrekToday.getTimestamp()).thenReturn(LocalDateTime.now());
        Mockito.when(cryptoCurrencyShrekToday.getPrice()).thenReturn(todayShrekPrice);
        CryptoCurrency cryptoCurrencyShrekTomorrow = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyShrekTomorrow.getTimestamp()).thenReturn(LocalDateTime.now().plusDays(1));
        Mockito.when(cryptoCurrencyShrekTomorrow.getPrice()).thenReturn(tomorrowShrekPrice);
        cryptoCurrenciesShrek = Arrays.asList(cryptoCurrencyShrekYesterday, cryptoCurrencyShrekToday, cryptoCurrencyShrekTomorrow);

        CryptoCurrency cryptoCurrencyMeowYesterday = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyMeowYesterday.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(1));
        Mockito.when(cryptoCurrencyMeowYesterday.getPrice()).thenReturn(yesterdayMeowPrice);
        CryptoCurrency cryptoCurrencyMeowToday = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyMeowToday.getTimestamp()).thenReturn(LocalDateTime.now());
        Mockito.when(cryptoCurrencyMeowToday.getPrice()).thenReturn(todayMeowPrice);
        CryptoCurrency cryptoCurrencyMeowTomorrow = Mockito.mock(CryptoCurrency.class);
        Mockito.when(cryptoCurrencyMeowTomorrow.getTimestamp()).thenReturn(LocalDateTime.now().plusDays(1));
        Mockito.when(cryptoCurrencyMeowTomorrow.getPrice()).thenReturn(tomorrowMeowPrice);
        cryptoCurrenciesMeow = Arrays.asList(cryptoCurrencyMeowYesterday, cryptoCurrencyMeowToday, cryptoCurrencyMeowTomorrow);
    }

    @Test
    public void getCurrencyData_daoReturnedData_dataReturned() {

        CryptoCurrency cryptoCurrency = Mockito.mock(CryptoCurrency.class);
        List<CryptoCurrency> expected = Collections.singletonList(cryptoCurrency);
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        List<CryptoCurrency> actual = cryptoCurrencyService.getCurrencyData(currencyShrek);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getCurrencyData_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getCurrencyData(currencyShrek);
    }

    @Test
    public void getOldest_daoReturnedData_oldestPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);

        Double actual = cryptoCurrencyService.getOldest(currencyShrek);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(yesterdayShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getOldest_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getOldest(currencyShrek);
    }

    @Test
    public void getNewest_daoReturnedData_newestPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);

        Double actual = cryptoCurrencyService.getNewest(currencyShrek);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(tomorrowShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getNewest_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getNewest(currencyShrek);
    }

    @Test
    public void getMin_daoReturnedData_minPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);

        Double actual = cryptoCurrencyService.getMin(currencyShrek);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(yesterdayShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getMin_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getMin(currencyShrek);
    }

    @Test
    public void getMax_daoReturnedData_maxPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);

        Double actual = cryptoCurrencyService.getMax(currencyShrek);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(todayShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getMax_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getMax(currencyShrek);
    }

    @Test
    public void getMinRanged_daoReturnedData_minPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        Double actual = cryptoCurrencyService.getMin(currencyShrek, start, end);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(yesterdayShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getMinRanged_daoReturnedNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        cryptoCurrencyService.getMin(currencyShrek, start, end);
    }

    @Test
    public void getMaxRanged_daoReturnedData_maxPriceReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        Double actual = cryptoCurrencyService.getMax(currencyShrek, start, end);

        Mockito.verify(cryptoCurrencyDao).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(todayShrekPrice, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getMaxRanged_daoReturnedDNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        cryptoCurrencyService.getMax(currencyShrek, start, end);
    }

    @Test
    public void getNormalizedRange_daoReturnedData_normalizedRangeReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        Double expected = (todayShrekPrice - yesterdayShrekPrice)/ yesterdayShrekPrice;

        Double actual = cryptoCurrencyService.getNormalizedRange(currencyShrek);

        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getNormalizedRange_daoReturnedDNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getNormalizedRange(currencyShrek);
    }

    @Test
    public void getNormalizedRangeRanged_daoReturnedData_normalizedRangeReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        Double expected = (todayShrekPrice - yesterdayShrekPrice)/ yesterdayShrekPrice;

        Double actual = cryptoCurrencyService.getNormalizedRange(currencyShrek, start, end);

        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = BadRequestException.class)
    public void getNormalizedRangeRanged_daoReturnedDNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        cryptoCurrencyService.getNormalizedRange(currencyShrek, start, end);
    }


    @Test
    public void getHighestNormalizedByRange_daoReturnedData_highestNormalizedRangeReturned() {

        Set<String> currencyNames = Set.of(currencyShrek, currencyMeow);
        Mockito.when(cryptoCurrencyDao.getAllCurrencyNames()).thenReturn(currencyNames);
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyMeow)).thenReturn(cryptoCurrenciesMeow);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        Double expectedNormalized = (todayMeowPrice - yesterdayMeowPrice)/yesterdayMeowPrice;
        CurrencyNormalizedView expected = new CurrencyNormalizedView(currencyMeow, expectedNormalized);

        CurrencyNormalizedView actual = cryptoCurrencyService.getHighestNormalizedByRange(start, end);

        Mockito.verify(cryptoCurrencyDao).getAllCurrencyNames();
        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyShrek);
        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyMeow);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getNormalizedValue(), actual.getNormalizedValue());
    }

    @Test(expected = BadRequestException.class)
    public void getHighestNormalizedByRange_daoReturnedDNull_BadRequestExceptionThrown() {

        Set<String> currencyNames = Set.of(currencyMeow);
        Mockito.when(cryptoCurrencyDao.getAllCurrencyNames()).thenReturn(currencyNames);
        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyMeow)).thenReturn(expected);
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        cryptoCurrencyService.getHighestNormalizedByRange(start, end);
    }

    @Test
    public void getCurrenciesListByNormalizedRangeDescending_daoReturnedData_descendingListReturned() {

        Set<String> currencyNames = Set.of(currencyShrek, currencyMeow);
        Mockito.when(cryptoCurrencyDao.getAllCurrencyNames()).thenReturn(currencyNames);
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyMeow)).thenReturn(cryptoCurrenciesMeow);
        Double expectedNormalizedMeow = (todayMeowPrice - yesterdayMeowPrice)/yesterdayMeowPrice;
        CurrencyNormalizedView expectedMeow = new CurrencyNormalizedView(currencyMeow, expectedNormalizedMeow);
        Double expectedNormalizedShrek = (todayShrekPrice - yesterdayShrekPrice)/yesterdayShrekPrice;
        CurrencyNormalizedView expectedShrek = new CurrencyNormalizedView(currencyShrek, expectedNormalizedShrek);
        List<CurrencyNormalizedView> expected = Arrays.asList(expectedMeow, expectedShrek);

        List<CurrencyNormalizedView> actual = cryptoCurrencyService.getCurrenciesListByNormalizedRangeDescending();

        Mockito.verify(cryptoCurrencyDao).getAllCurrencyNames();
        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyShrek);
        Mockito.verify(cryptoCurrencyDao, Mockito.times(2)).getCurrencyData(currencyMeow);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected.get(0).getName(), actual.get(0).getName());
        Assert.assertEquals(expected.get(1).getName(), actual.get(1).getName());
        Assert.assertEquals(expected.get(0).getNormalizedValue(), actual.get(0).getNormalizedValue());
        Assert.assertEquals(expected.get(1).getNormalizedValue(), actual.get(1).getNormalizedValue());
    }

    @Test(expected = BadRequestException.class)
    public void getCurrenciesListByNormalizedRangeDescending_daoReturnedDNull_BadRequestExceptionThrown() {

        Set<String> currencyNames = Set.of(currencyMeow);
        Mockito.when(cryptoCurrencyDao.getAllCurrencyNames()).thenReturn(currencyNames);
        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyMeow)).thenReturn(expected);

        cryptoCurrencyService.getCurrenciesListByNormalizedRangeDescending();
    }

    @Test
    public void getCurrencyStatistics_daoReturnedData_statisticsReturned() {

        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(cryptoCurrenciesShrek);
        CurrencyStatisticsView expected = new CurrencyStatisticsView(currencyShrek, yesterdayShrekPrice, tomorrowShrekPrice, yesterdayShrekPrice, todayShrekPrice);
        CurrencyStatisticsView actual = cryptoCurrencyService.getCurrencyStatistics(currencyShrek);

        Mockito.verify(cryptoCurrencyDao, Mockito.times(4)).getCurrencyData(currencyShrek);
        Mockito.verifyNoMoreInteractions(cryptoCurrencyDao);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOldest(), actual.getOldest());
        Assert.assertEquals(expected.getNewest(), actual.getNewest());
        Assert.assertEquals(expected.getMax(), actual.getMax());
        Assert.assertEquals(expected.getMin(), actual.getMin());
    }

    @Test(expected = BadRequestException.class)
    public void getCurrencyStatistics_daoReturnedDNull_BadRequestExceptionThrown() {

        List<CryptoCurrency> expected = null;
        Mockito.when(cryptoCurrencyDao.getCurrencyData(currencyShrek)).thenReturn(expected);

        cryptoCurrencyService.getCurrencyStatistics(currencyShrek);
    }
}
