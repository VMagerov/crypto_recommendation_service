package com.epam.domain;

public class CurrencyStatisticsView {

    private String name;

    private Double oldest;

    private Double newest;

    private Double min;

    private Double max;

    public CurrencyStatisticsView(String name, Double oldest, Double newest, Double min, Double max) {
        this.name = name;
        this.oldest = oldest;
        this.newest = newest;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOldest() {
        return oldest;
    }

    public void setOldest(Double oldest) {
        this.oldest = oldest;
    }

    public Double getNewest() {
        return newest;
    }

    public void setNewest(Double newest) {
        this.newest = newest;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
