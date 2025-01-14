package org.kobeU.stock_view.model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockService {
    private Retrofit retrofit;
    private YahooFinanceService service;

    public StockService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://query2.finance.yahoo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(YahooFinanceService.class);
    }

    public Call<StockResponse> getStockData(String symbol) {
        return service.getStockData(symbol);
    }

    public static void main(String[] args) {
        StockService stockService = new StockService();
        Call<StockResponse> stockData = stockService.getStockData("AAPL");
        try {
            StockResponse response = stockData.execute().body();
            System.out.println(response.getChart().getResult().get(0).getIndicators().getQuote().get(0).getClose());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
