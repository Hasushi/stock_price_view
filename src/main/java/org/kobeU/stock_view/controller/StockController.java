package org.kobeU.stock_view.controller;

import org.kobeU.stock_view.model.Meta;
import org.kobeU.stock_view.model.Result;
import org.kobeU.stock_view.model.StockResponse;
import org.kobeU.stock_view.model.StockService;
import org.kobeU.stock_view.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockController {

    private final StockService stockService;
    private final MainView mainView;
    private Timer timer;

    public StockController(StockService stockService, MainView mainView) {
        this.stockService = stockService;
        this.mainView = mainView;

        // リスナーを設定
        this.mainView.setFetchListener(e -> {
            String symbol = mainView.getSelectedSymbol();
            startFetchingData(symbol); // 2秒ごとにデータを取得
        });
    }

    public void startFetchingData(String symbol) {
        mainView.resetTable();
        // すでにTimerが動作中の場合は停止
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // 2秒ごとにデータを取得するTimerを設定
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestStockData(symbol);
            }
        });
        timer.start();
    }

    public void stopFetchingData() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void requestStockData(String symbol) {
        Call<StockResponse> call = stockService.getStockData(symbol);

        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StockResponse stockResponse = response.body();
                    handleStockResponse(stockResponse);
                } else {
                    mainView.showError("Failed to fetch stock data. Response error.");
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                mainView.showError("Network error: " + t.getMessage());
            }
        });
    }

    private void handleStockResponse(StockResponse stockResponse) {
        Result result = stockResponse.getChart().getResult().get(0);
        Meta meta = result.getMeta();
        List<Double> closePrices = result.getIndicators().getQuote().get(0).getClose();
        List<Long> timestamps = result.getTimestamp();

        double price = meta.getRegularMarketPrice();
        double changePercent = calculateChangePercent(meta);

        mainView.updateTable(meta.getSymbol(), price, changePercent);
        mainView.updateChart(closePrices, timestamps);
    }

    private double calculateChangePercent(Meta meta) {
        double previousClose = meta.getPreviousClose();
        double currentPrice = meta.getRegularMarketPrice();
        return ((currentPrice - previousClose) / previousClose) * 100;
    }
}
