package org.kobeU.stock_view.model;

import java.util.List;

public class Quote {
    private List<Double> high;
    private List<Double> low;
    private List<Double> close;
    private List<Integer> volume;

    public List<Double> getHigh() {
        return high;
    }

    public void setHigh(List<Double> high) {
        this.high = high;
    }

    public List<Double> getLow() {
        return low;
    }

    public void setLow(List<Double> low) {
        this.low = low;
    }

    public List<Double> getClose() {
        return close;
    }

    public void setClose(List<Double> close) {
        this.close = close;
    }

    public List<Integer> getVolume() {
        return volume;
    }

    public void setVolume(List<Integer> volume) {
        this.volume = volume;
    }
}
