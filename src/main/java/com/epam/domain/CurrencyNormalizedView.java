package com.epam.domain;

public class CurrencyNormalizedView {

    private String name;

    private Double normalizedValue;

    public CurrencyNormalizedView(String name, Double normalizedValue) {
        this.name = name;
        this.normalizedValue = normalizedValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalizedValue(Double normalizedValue) {
        this.normalizedValue = normalizedValue;
    }
}
