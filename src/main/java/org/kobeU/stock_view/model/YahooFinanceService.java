package org.kobeU.stock_view.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YahooFinanceService {
    @GET("v8/finance/chart/{symbols}")
    Call<StockResponse> getStockData(@Path("symbols") String symbols);
}