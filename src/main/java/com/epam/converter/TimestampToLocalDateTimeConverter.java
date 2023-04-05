package com.epam.converter;

import com.opencsv.bean.AbstractBeanField;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampToLocalDateTimeConverter extends AbstractBeanField {

    @Override
    protected LocalDateTime convert(String value) {
        LocalDateTime date = Instant.ofEpochMilli(Long.parseLong(value)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return date;
    }
}
