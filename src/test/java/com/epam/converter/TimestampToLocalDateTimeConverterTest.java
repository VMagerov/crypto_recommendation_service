package com.epam.converter;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampToLocalDateTimeConverterTest {

    private TimestampToLocalDateTimeConverter converter = new TimestampToLocalDateTimeConverter();
    @Test
    public void convert_correctTimestamp_convertedLocalDateTimeReturned() {
        String timestamp = "1641009600000";
        LocalDateTime expected = Instant.ofEpochMilli(Long.parseLong(timestamp)).atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime actual = converter.convert(timestamp);

        Assert.assertEquals(expected, actual);
    }
}
