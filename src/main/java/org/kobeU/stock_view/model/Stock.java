package org.kobeU.stock_view.model;

public class Stock {
    private String symbol;
    private double regularMarketPrice;
    private double changePercent;

    // Constructor
    public Stock(String symbol, double regularMarketPrice, double changePercent) {
        this.symbol = symbol;
        this.regularMarketPrice = regularMarketPrice;
        this.changePercent = changePercent;
    }

    // Getter for symbol
    public String getSymbol() {
        return symbol;
    }

    // Getter for regularMarketPrice
    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    // Getter for changePercent
    public double getChangePercent() {
        return changePercent;
    }
}